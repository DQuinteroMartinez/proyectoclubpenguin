package co.edu.unbosque.clubpenguin.configuration;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.clubpenguin.model.Rol;
import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.RolRepository;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;
import io.jsonwebtoken.security.Password;
import co.edu.unbosque.clubpenguin.model.Minijuego;
import co.edu.unbosque.clubpenguin.repository.MinijuegoRepository;

/**
 * Carga los datos iniciales que el sistema necesita para funcionar. Por ahora
 * crea los roles ADMIN y USUARIO si todavía no existen.
 */
@Configuration
public class LoadDatabase {

	/**
	 * Nombre de usuario del administrador inicial, leído de application.properties.
	 */
	@Value("${admin.usuario}")
	private String adminUsuario;

	/** Correo del administrador inicial, leído de application.properties. */
	@Value("${admin.correo}")
	private String adminCorreo;

	/** Contraseña del administrador inicial, leída de application.properties. */
	@Value("${admin.contrasena}")
	private String adminContrasena;

	/**
	 * Crea los roles por defecto cada vez que arranca la aplicación. Si un rol ya
	 * existe en la base de datos, no lo vuelve a crear.
	 * 
	 * @param rolRepository   repositorio que permite buscar y guardar roles
	 *                        * @param usuarioRepository repositorio que permite
	 *                        buscar y guardar usuarios
	 * @param passwordEncoder cifrador para proteger la contraseña del administrador
	 * @return una tarea que Spring ejecuta justo después de iniciar la aplicación
	 */

	@Bean
	CommandLineRunner cargarDatosIniciales(RolRepository rolRepository, UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			Rol rolAdmin = rolRepository.findByNombre("ADMIN").orElseGet(() -> rolRepository.save(new Rol("ADMIN")));

			if (rolRepository.findByNombre("USUARIO").isEmpty()) {
				rolRepository.save(new Rol("USUARIO"));
			}
			if (!usuarioRepository.existsByNombreUsuario(adminUsuario)) {
				Usuario admin = new Usuario();
				admin.setNombreUsuario(adminUsuario);
				admin.setCorreo(adminCorreo);
				admin.setContrasena(passwordEncoder.encode(adminContrasena));
				admin.setFechaRegistro(LocalDateTime.now());
				admin.setRol(rolAdmin);
				usuarioRepository.save(admin);
			}
		};
	}

	/**
	 * Crea los cinco minijuegos del sistema si todavía no existen.
	 *
	 * @param minijuegoRepository repositorio que permite buscar y guardar
	 *                            minijuegos
	 * @return una tarea que Spring ejecuta justo después de iniciar la aplicación
	 */
	@Bean
	public CommandLineRunner cargarMinijuegos(MinijuegoRepository minijuegoRepository) {
		return args -> {
			crearMinijuegoSiNoExiste(minijuegoRepository, "Card-Jitsu",
					"Juego de cartas por turnos con los elementos fuego, agua y nieve");
			crearMinijuegoSiNoExiste(minijuegoRepository, "Atrapabolsas",
					"Atrapa las bolsas que caen antes de que toquen el suelo");
			crearMinijuegoSiNoExiste(minijuegoRepository, "Pizzatrón 3000",
					"Prepara y sirve las pizzas que piden los clientes");
			crearMinijuegoSiNoExiste(minijuegoRepository, "Carrito Surfero",
					"Recorre la mina en un carrito esquivando los obstáculos");
			crearMinijuegoSiNoExiste(minijuegoRepository, "Concurso de Baile",
					"Sigue el ritmo y las flechas para sumar puntos");
		};

	}

	/**
	 * Guarda un minijuego solo si no existe ya otro con el mismo nombre.
	 *
	 * @param repositorio repositorio de minijuegos
	 * @param nombre      nombre del minijuego
	 * @param descripcion breve descripción del minijuego
	 */
	private void crearMinijuegoSiNoExiste(MinijuegoRepository repositorio, String nombre, String descripcion) {
		if (repositorio.findByNombre(nombre).isEmpty()) {
			repositorio.save(new Minijuego(nombre, descripcion));
		}
	}
}
