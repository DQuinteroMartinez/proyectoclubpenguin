package co.edu.unbosque.clubpenguin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.clubpenguin.dto.NuevaPartidaDto;
import co.edu.unbosque.clubpenguin.dto.PartidaDto;
import co.edu.unbosque.clubpenguin.exception.RecursoNoEncontradoException;
import co.edu.unbosque.clubpenguin.model.EstadoReto;
import co.edu.unbosque.clubpenguin.model.Minijuego;
import co.edu.unbosque.clubpenguin.model.Partida;
import co.edu.unbosque.clubpenguin.model.Reto;
import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.MinijuegoRepository;
import co.edu.unbosque.clubpenguin.repository.PartidaRepository;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;
import co.edu.unbosque.clubpenguin.repository.RetoRepository;

/**
 * Contiene la lógica de negocio relacionada con las partidas: 
 * registrar el puntaje de una partida y consultar el historial de un usuario.
 */
@Service
public class PartidaService {

	/** Registro de mensajes del sistema, para avisar de problemas sin interrumpir al jugador. */
    private static final Logger log = LoggerFactory.getLogger(PartidaService.class);

	/** Repositorio para guardar y consultar partidas. */
	private final PartidaRepository partidaRepository;

	/** Repositorio para consultar usuarios. */
	private final UsuarioRepository usuarioRepository;

	/** Repositorio para consultar minijuegos. */
	private final MinijuegoRepository minijuegoRepository;
	/** Repositorio para consultar y actualizar los retos. */
    private final RetoRepository retoRepository;

    /** Service que envía los correos. */
    private final CorreoService correoService;

	/**
	 * Crea el service. Spring entrega automáticamente cada dependencia.
	 *
	 * @param partidaRepository   repositorio de partidas
	 * @param usuarioRepository   repositorio de usuarios
	 * @param minijuegoRepository repositorio de minijuegos
	 * @param retoRepository repositorio de retos
     * @param correoService service de correos
	 */
	public PartidaService(PartidaRepository partidaRepository, UsuarioRepository usuarioRepository,
			MinijuegoRepository minijuegoRepository, RetoRepository retoRepository,
            CorreoService correoService) {
		this.partidaRepository = partidaRepository;
		this.usuarioRepository = usuarioRepository;
		this.minijuegoRepository = minijuegoRepository;
		this.retoRepository = retoRepository;
        this.correoService = correoService;
	}

	/**
	 * Registra una partida terminada para el usuario indicado.
	 *
	 * @param nombreUsuario   nombre del usuario que jugó, tomado del token
	 * @param nuevaPartidaDto minijuego jugado y puntaje obtenido
	 * @return los datos de la partida guardada
	 * @throws RecursoNoEncontradoException si el usuario o el minijuego no existen
	 */
	public PartidaDto registrar(String nombreUsuario, NuevaPartidaDto nuevaPartidaDto) {
		Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

		Minijuego minijuego = minijuegoRepository.findById(nuevaPartidaDto.getMinijuegoId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Minijuego no encontrado"));

		Partida partida = new Partida();
		partida.setUsuario(usuario);
		partida.setMinijuego(minijuego);
		partida.setPuntaje(nuevaPartidaDto.getPuntaje());
		partida.setFecha(LocalDateTime.now());
		Partida guardada = partidaRepository.save(partida);
		resolverRetosPendientes(usuario, minijuego, nuevaPartidaDto.getPuntaje());

		return convertirADto(partidaRepository.save(partida));
	}

	/**
	 * Obtiene el historial de partidas de un usuario, de la más reciente a la más
	 * antigua.
	 *
	 * @param nombreUsuario nombre del usuario, tomado del token
	 * @return la lista de partidas del usuario
	 */
	public List<PartidaDto> historial(String nombreUsuario) {
		return partidaRepository.findByUsuarioNombreUsuarioOrderByFechaDesc(nombreUsuario).stream()
				.map(this::convertirADto).collect(Collectors.toList());
	}
	/**
     * Resuelve los retos pendientes que tiene un jugador en un minijuego,
     * usando el puntaje de la partida que acaba de jugar.
     * Un reto se supera solo si el puntaje es mayor que el objetivo.
     *
     * @param retado jugador que acaba de jugar la partida
     * @param minijuego minijuego en el que jugó
     * @param puntaje puntaje que obtuvo en la partida
     */
    private void resolverRetosPendientes(Usuario retado, Minijuego minijuego, int puntaje) {
        List<Reto> pendientes = retoRepository.findByRetadoNombreUsuarioAndMinijuegoIdAndEstado(
                retado.getNombreUsuario(), minijuego.getId(), EstadoReto.PENDIENTE);

        for (Reto reto : pendientes) {
            boolean superado = puntaje > reto.getPuntajeObjetivo();

            reto.setPuntajeRetado(puntaje);
            reto.setEstado(superado ? EstadoReto.SUPERADO : EstadoReto.NO_SUPERADO);
            reto.setFechaResolucion(LocalDateTime.now());
            retoRepository.save(reto);

            avisarResultado(reto, superado);
        }
    }

    /**
     * Avisa por correo al retador cómo terminó su reto.
     * Si el correo falla, solo se anota en el registro del sistema: la partida
     * y el resultado del reto no se pierden.
     *
     * @param reto reto que acaba de resolverse
     * @param superado true si el retado superó el puntaje objetivo
     */
    private void avisarResultado(Reto reto, boolean superado) {
        String resultado = superado ? "superó" : "no superó";
        String mensaje = "Hola " + reto.getRetador().getNombreUsuario() + ",\n\n"
                + reto.getRetado().getNombreUsuario() + " " + resultado + " tu reto en "
                + reto.getMinijuego().getNombre() + ".\n"
                + "Tu puntaje: " + reto.getPuntajeObjetivo() + "\n"
                + "Puntaje de " + reto.getRetado().getNombreUsuario() + ": " + reto.getPuntajeRetado();

        try {
            correoService.enviarCorreo(reto.getRetador().getCorreo(),
                    "Resultado de tu reto en Club Penguin", mensaje);
        } catch (MailException e) {
            log.warn("No se pudo enviar el resultado del reto {}: {}", reto.getId(), e.getMessage());
        }
    }

	/**
     * Convierte una partida de la base de datos en un DTO para mostrar.
     *
     * @param partida partida que se quiere convertir
     * @return el DTO con los datos de la partida
     */
    private PartidaDto convertirADto(Partida partida) {
        PartidaDto dto = new PartidaDto();
        dto.setId(partida.getId());
        dto.setNombreUsuario(partida.getUsuario().getNombreUsuario());
        dto.setMinijuego(partida.getMinijuego().getNombre());
        dto.setPuntaje(partida.getPuntaje());
        dto.setFecha(partida.getFecha());
        return dto;
    }
}
