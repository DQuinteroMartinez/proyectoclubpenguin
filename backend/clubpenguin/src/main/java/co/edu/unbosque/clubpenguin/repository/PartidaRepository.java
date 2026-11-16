package co.edu.unbosque.clubpenguin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.clubpenguin.model.Partida;

/**
 * Repositorio para acceder a la tabla de partidas. Hereda de JpaRepository los
 * métodos básicos para guardar, buscar, listar y borrar.
 */
public interface PartidaRepository extends JpaRepository<Partida, Long> {

	/**
	 * Obtiene las partidas de un usuario, de la más reciente a la más antigua.
	 *
	 * @param nombreUsuario nombre del usuario dueño de las partidas
	 * @return la lista de partidas del usuario ordenada por fecha descendente
	 */
	List<Partida> findByUsuarioNombreUsuarioOrderByFechaDesc(String nombreUsuario);

	/**
	 * Obtiene todas las partidas jugadas en un minijuego.
	 *
	 * @param minijuegoId identificador del minijuego
	 * @return la lista de partidas de ese minijuego
	 */
	List<Partida> findByMinijuegoId(Long minijuegoId);

	/**
	 * Obtiene las partidas de un usuario en un minijuego concreto.
	 *
	 * @param nombreUsuario nombre del usuario dueño de las partidas
	 * @param minijuegoId   identificador del minijuego
	 * @return la lista de partidas del usuario en ese minijuego
	 */
	List<Partida> findByUsuarioNombreUsuarioAndMinijuegoId(String nombreUsuario, Long minijuegoId);

	/**
	 * Elimina todas las partidas de un usuario.
	 *
	 * @param usuarioId identificador del usuario cuyas partidas se eliminarán
	 */
	void deleteByUsuarioId(Long usuarioId);

}
