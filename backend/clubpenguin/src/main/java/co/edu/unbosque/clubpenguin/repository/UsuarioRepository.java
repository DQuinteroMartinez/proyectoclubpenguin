package co.edu.unbosque.clubpenguin.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.clubpenguin.model.Usuario;

/**
 * Repositorio para acceder a la tabla de usuarios. Hereda de JpaRepository los
 * métodos básicos (guardar, buscar, listar y borrar).
 */

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Busca un usuario por su nombre de usuario.
	 *
	 * @param nombreUsuario nombre de usuario a buscar
	 * @return un Optional con el usuario si existe, o vacío si no se encontró
	 */
	Optional<Usuario> findByNombreUsuario(String nombreUsuario);

	/**
	 * Busca un usuario por su correo electrónico.
	 *
	 * @param correo correo a buscar
	 * @return un Optional con el usuario si existe, o vacío si no se encontró
	 */
	Optional<Usuario> findByCorreo(String correo);

	/**
	 * Verifica si ya existe un usuario con ese nombre de usuario.
	 *
	 * @param nombreUsuario nombre de usuario a verificar
	 * @return true si ya está registrado, false en caso contrario
	 */
	boolean existsByNombreUsuario(String nombreUsuario);

	/**
	 * Verifica si ya existe un usuario con ese correo.
	 *
	 * @param correo correo a verificar
	 * @return true si ya está registrado, false en caso contrario
	 */

	boolean existsByCorreo(String correo);

}
