package co.edu.unbosque.clubpenguin.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unbosque.clubpenguin.model.Rol;
import co.edu.unbosque.clubpenguin.repository.RolRepository;

/**
 * Carga los datos iniciales que el sistema necesita para funcionar. Por ahora
 * crea los roles ADMIN y USUARIO si todavía no existen.
 */
@Configuration
public class LoadDatabase {

	/**
	 * Crea los roles por defecto cada vez que arranca la aplicación. Si un rol ya
	 * existe en la base de datos, no lo vuelve a crear.
	 * 
	 * @param rolRepository repositorio que permite buscar y guardar roles
	 * @return una tarea que Spring ejecuta justo después de iniciar la aplicación
	 */

	@Bean CommandLineRunner cargarRoles(RolRepository rolRepository) {
		return args -> {
			if (rolRepository.findByNombre("ADMIN").isEmpty()) {
				rolRepository.save(new Rol("ADMIN"));

			}
			if (rolRepository.findByNombre("USUARIO").isEmpty()) {
				rolRepository.save(new Rol("USUARIO"));
			}
		};

	}
}
