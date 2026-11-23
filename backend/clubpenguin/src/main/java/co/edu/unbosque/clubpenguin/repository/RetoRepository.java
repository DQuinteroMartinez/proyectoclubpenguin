package co.edu.unbosque.clubpenguin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.clubpenguin.model.Reto;
import co.edu.unbosque.clubpenguin.model.EstadoReto;

/**
 * Repositorio para acceder a la tabla de retos. Hereda de JpaRepository los
 * métodos básicos para guardar, buscar, listar y borrar.
 */

public interface RetoRepository extends JpaRepository<Reto, Long> {

	/**
	 * Obtiene los retos en los que un usuario participa, ya sea como retador o como
	 * retado, del más reciente al más antiguo.
	 *
	 * @param nombreRetador nombre de usuario buscado como retador
	 * @param nombreRetado  nombre de usuario buscado como retado
	 * @return la lista de retos del usuario ordenada por fecha de creación
	 *         descendente
	 */
	List<Reto> findByRetadorNombreUsuarioOrRetadoNombreUsuarioOrderByFechaCreacionDesc(String nombreRetador,
			String nombreRetado);

	/**
	 * Obtiene los retos de un jugador retado, en un minijuego y con un estado
	 * concreto. Se usa para encontrar los retos pendientes que una partida nueva
	 * puede resolver.
	 *
	 * @param nombreRetado nombre de usuario del jugador retado
	 * @param minijuegoId  identificador del minijuego
	 * @param estado       estado que deben tener los retos buscados
	 * @return la lista de retos que cumplen las tres condiciones
	 */
	List<Reto> findByRetadoNombreUsuarioAndMinijuegoIdAndEstado(String nombreRetado, Long minijuegoId,
			EstadoReto estado);

	/**
	 * Elimina todos los retos en los que participa un usuario, como retador o como
	 * retado.
	 *
	 * @param retadorId identificador del usuario buscado como retador
	 * @param retadoId  identificador del usuario buscado como retado
	 */
	void deleteByRetadorIdOrRetadoId(Long retadorId, Long retadoId);

}
