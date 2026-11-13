package co.edu.unbosque.clubpenguin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.dto.NuevaPartidaDto;
import co.edu.unbosque.clubpenguin.dto.PartidaDto;
import co.edu.unbosque.clubpenguin.exception.RecursoNoEncontradoException;
import co.edu.unbosque.clubpenguin.model.Minijuego;
import co.edu.unbosque.clubpenguin.model.Partida;
import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.MinijuegoRepository;
import co.edu.unbosque.clubpenguin.repository.PartidaRepository;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;

/**
 * Contiene la lógica de negocio relacionada con las partidas: registrar el
 * puntaje de una partida y consultar el historial de un usuario.
 */
@Service
public class PartidaService {

	/** Repositorio para guardar y consultar partidas. */
	private final PartidaRepository partidaRepository;

	/** Repositorio para consultar usuarios. */
	private final UsuarioRepository usuarioRepository;

	/** Repositorio para consultar minijuegos. */
	private final MinijuegoRepository minijuegoRepository;

	/**
	 * Crea el service. Spring entrega automáticamente cada dependencia.
	 *
	 * @param partidaRepository   repositorio de partidas
	 * @param usuarioRepository   repositorio de usuarios
	 * @param minijuegoRepository repositorio de minijuegos
	 */
	public PartidaService(PartidaRepository partidaRepository, UsuarioRepository usuarioRepository,
			MinijuegoRepository minijuegoRepository) {
		this.partidaRepository = partidaRepository;
		this.usuarioRepository = usuarioRepository;
		this.minijuegoRepository = minijuegoRepository;
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
