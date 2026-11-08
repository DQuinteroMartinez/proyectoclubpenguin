package co.edu.unbosque.clubpenguin.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.clubpenguin.model.Rol;

/**
 * Repositorio para acceder a la tabla de roles.
 * Hereda de JpaRepository los métodos básicos (guardar, buscar, listar y borrar).
 */

public interface RolRepository extends JpaRepository<Rol, Long> {
	
    /**
     * Busca un rol por su nombre.
     *
     * @param nombre nombre del rol (por ejemplo, "ADMIN" o "USUARIO")
     * @return un Optional con el rol si existe, o vacío si no se encontró
     */
	
	Optional<Rol> findByNombre(String nombre);

}
