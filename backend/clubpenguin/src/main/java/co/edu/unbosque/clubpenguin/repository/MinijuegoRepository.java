package co.edu.unbosque.clubpenguin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.clubpenguin.model.Minijuego;

/**
 * Repositorio para acceder a la tabla de minijuegos. Hereda de JpaRepository
 * los métodos básicos para guardar, buscar, listar y borrar.
 */
public interface MinijuegoRepository extends JpaRepository<Minijuego, Long> {

	/**
	 * Busca un minijuego por su nombre.
	 *
	 * @param nombre nombre del minijuego a buscar
	 * @return un Optional con el minijuego si existe, o vacío si no se encontró
	 */
	Optional<Minijuego> findByNombre(String nombre);
}
