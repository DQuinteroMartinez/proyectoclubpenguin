package co.edu.unbosque.clubpenguin.service;

import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.dto.PosicionDto;
import co.edu.unbosque.clubpenguin.exception.RecursoNoEncontradoException;
import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;

/**
 * Contiene la lógica de negocio de la posición de los jugadores en el mapa:
 * guardar la última posición y consultarla al volver a entrar.
 */
@Service

public class PosicionService {

	/** Repositorio para consultar y actualizar usuarios. */
	private final UsuarioRepository usuarioRepository;

	/**
	 * Crea el service. Spring entrega automáticamente el repositorio.
	 *
	 * @param usuarioRepository repositorio de usuarios
	 */
	public PosicionService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Obtiene la última posición guardada de un jugador. Si el jugador nunca se ha
	 * movido, devuelve la posición (0, 0).
	 *
	 * @param nombreUsuario nombre del jugador, tomado del token
	 * @return la posición actual del jugador
	 * @throws RecursoNoEncontradoException si el usuario no existe
	 */
	public PosicionDto obtener(String nombreUsuario) {
		Usuario usuario = buscarUsuario(nombreUsuario);

		int x = usuario.getPosicionX() == null ? 0 : usuario.getPosicionX();
		int y = usuario.getPosicionY() == null ? 0 : usuario.getPosicionY();

		return new PosicionDto(x, y);
	}

	/**
	 * Guarda la nueva posición de un jugador.
	 *
	 * @param nombreUsuario nombre del jugador, tomado del token
	 * @param posicionDto   nueva posición enviada por el juego
	 * @return la posición que quedó guardada
	 * @throws RecursoNoEncontradoException si el usuario no existe
	 */
	public PosicionDto actualizar(String nombreUsuario, PosicionDto posicionDto) {
		Usuario usuario = buscarUsuario(nombreUsuario);

		usuario.setPosicionX(posicionDto.getX());
		usuario.setPosicionY(posicionDto.getY());
		usuarioRepository.save(usuario);

		return new PosicionDto(usuario.getPosicionX(), usuario.getPosicionY());
	}

	/**
	 * Busca un usuario por su nombre o lanza una excepción si no existe.
	 *
	 * @param nombreUsuario nombre del usuario buscado
	 * @return el usuario encontrado
	 * @throws RecursoNoEncontradoException si el usuario no existe
	 */
	private Usuario buscarUsuario(String nombreUsuario) {
		return usuarioRepository.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
	}

}
