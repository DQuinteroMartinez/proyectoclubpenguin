package co.edu.unbosque.clubpenguin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unbosque.clubpenguin.dto.NuevoRetoDto;
import co.edu.unbosque.clubpenguin.dto.RetoDto;
import co.edu.unbosque.clubpenguin.exception.OperacionInvalidaException;
import co.edu.unbosque.clubpenguin.exception.RecursoNoEncontradoException;
import co.edu.unbosque.clubpenguin.model.EstadoReto;
import co.edu.unbosque.clubpenguin.model.Minijuego;
import co.edu.unbosque.clubpenguin.model.Partida;
import co.edu.unbosque.clubpenguin.model.Reto;
import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.MinijuegoRepository;
import co.edu.unbosque.clubpenguin.repository.PartidaRepository;
import co.edu.unbosque.clubpenguin.repository.RetoRepository;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;

/**
 * Contiene la lógica de negocio de los retos entre jugadores: crear un reto,
 * avisar por correo al retado y consultar el historial de retos.
 */
@Service
public class RetoService {

	/** Repositorio para guardar y consultar retos. */
	private final RetoRepository retoRepository;

	/** Repositorio para consultar usuarios. */
	private final UsuarioRepository usuarioRepository;

	/** Repositorio para consultar minijuegos. */
	private final MinijuegoRepository minijuegoRepository;

	/** Repositorio para consultar las partidas del retador. */
	private final PartidaRepository partidaRepository;

	/** Service que envía los correos. */
	private final CorreoService correoService;

	/**
	 * Crea el service. Spring entrega automáticamente cada dependencia.
	 *
	 * @param retoRepository      repositorio de retos
	 * @param usuarioRepository   repositorio de usuarios
	 * @param minijuegoRepository repositorio de minijuegos
	 * @param partidaRepository   repositorio de partidas
	 * @param correoService       service de correos
	 */
	public RetoService(RetoRepository retoRepository, UsuarioRepository usuarioRepository,
			MinijuegoRepository minijuegoRepository, PartidaRepository partidaRepository, CorreoService correoService) {
		this.retoRepository = retoRepository;
		this.usuarioRepository = usuarioRepository;
		this.minijuegoRepository = minijuegoRepository;
		this.partidaRepository = partidaRepository;
		this.correoService = correoService;
	}

	/**
	 * Crea un reto y le avisa por correo al jugador retado. El puntaje a superar es
	 * el mejor que el retador ha obtenido en ese minijuego. Si el correo no se
	 * puede enviar, el reto no se guarda.
	 *
	 * @param nombreRetador nombre del jugador que lanza el reto, tomado del token
	 * @param nuevoRetoDto  jugador retado y minijuego del reto
	 * @return los datos del reto creado
	 * @throws OperacionInvalidaException   si el jugador se reta a sí mismo o no ha
	 *                                      jugado ese minijuego
	 * @throws RecursoNoEncontradoException si el retador, el retado o el minijuego
	 *                                      no existen
	 */
	@Transactional
	public RetoDto crear(String nombreRetador, NuevoRetoDto nuevoRetoDto) {
		if (nombreRetador.equals(nuevoRetoDto.getNombreUsuarioRetado())) {
			throw new OperacionInvalidaException("No puedes retarte a ti mismo");
		}

		Usuario retador = usuarioRepository.findByNombreUsuario(nombreRetador)
				.orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

		Usuario retado = usuarioRepository.findByNombreUsuario(nuevoRetoDto.getNombreUsuarioRetado())
				.orElseThrow(() -> new RecursoNoEncontradoException("El usuario retado no existe"));

		Minijuego minijuego = minijuegoRepository.findById(nuevoRetoDto.getMinijuegoId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Minijuego no encontrado"));

		List<Partida> partidas = partidaRepository.findByUsuarioNombreUsuarioAndMinijuegoId(nombreRetador,
				minijuego.getId());
		if (partidas.isEmpty()) {
			throw new OperacionInvalidaException("Debes jugar al menos una partida de este minijuego para poder retar");
		}

		int mejorPuntaje = 0;
		for (Partida partida : partidas) {
			if (partida.getPuntaje() > mejorPuntaje) {
				mejorPuntaje = partida.getPuntaje();
			}
		}

		Reto reto = new Reto();
		reto.setRetador(retador);
		reto.setRetado(retado);
		reto.setMinijuego(minijuego);
		reto.setPuntajeObjetivo(mejorPuntaje);
		reto.setEstado(EstadoReto.PENDIENTE);
		reto.setFechaCreacion(LocalDateTime.now());
		Reto guardado = retoRepository.save(reto);

		String mensaje = "Hola " + retado.getNombreUsuario() + ",\n\n" + retador.getNombreUsuario() + " te reta en "
				+ minijuego.getNombre() + ".\n" + "Para ganar el reto debes superar " + mejorPuntaje + " puntos.\n\n"
				+ "Inicia sesión en Club Penguin y juega una partida en ese minijuego: "
				+ "el sistema registrará el resultado del reto automáticamente.";
		correoService.enviarCorreo(retado.getCorreo(), "Has recibido un reto en Club Penguin", mensaje);

		return convertirADto(guardado);
	}

	/**
	 * Obtiene el historial de retos de un usuario, tanto los que lanzó como los que
	 * recibió, del más reciente al más antiguo.
	 *
	 * @param nombreUsuario nombre del usuario, tomado del token
	 * @return la lista de retos del usuario
	 */
	public List<RetoDto> listarMios(String nombreUsuario) {
		return retoRepository
				.findByRetadorNombreUsuarioOrRetadoNombreUsuarioOrderByFechaCreacionDesc(nombreUsuario, nombreUsuario)
				.stream().map(this::convertirADto).collect(Collectors.toList());
	}

	/**
	 * Convierte un reto de la base de datos en un DTO para mostrar.
	 *
	 * @param reto reto que se quiere convertir
	 * @return el DTO con los datos del reto
	 */
	private RetoDto convertirADto(Reto reto) {
		RetoDto dto = new RetoDto();
		dto.setId(reto.getId());
		dto.setRetador(reto.getRetador().getNombreUsuario());
		dto.setRetado(reto.getRetado().getNombreUsuario());
		dto.setMinijuego(reto.getMinijuego().getNombre());
		dto.setPuntajeObjetivo(reto.getPuntajeObjetivo());
		dto.setPuntajeRetado(reto.getPuntajeRetado());
		dto.setEstado(reto.getEstado().name());
		dto.setFechaCreacion(reto.getFechaCreacion());
		dto.setFechaResolucion(reto.getFechaResolucion());
		return dto;
	}

}
