package co.edu.unbosque.clubpenguin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.List;

import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración de seguridad de la aplicación. Por ahora solo define el
 * cifrador de contraseñas y deja las rutas abiertas. Más adelante se
 * reemplazará por la protección con JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/** Filtro que revisa el token JWT en cada petición. */
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * Crea la configuración. Spring entrega automáticamente el filtro.
	 *
	 * @param jwtAuthenticationFilter filtro de autenticación con JWT
	 */
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	/**
	 * Crea el cifrador que se usará para guardar las contraseñas de forma segura.
	 *
	 * @return un cifrador BCrypt
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Expone el componente que verifica usuario y contraseña durante el login.
	 *
	 * @param configuracion configuración de autenticación que arma Spring
	 *                      automáticamente
	 * @return el administrador de autenticación
	 * @throws Exception si ocurre un error al obtenerlo
	 */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuracion) throws Exception {
		return configuracion.getAuthenticationManager();
	}

	/**
	 * Define las reglas de acceso a las rutas de la API. El registro y el login son
	 * públicos; todo lo demás exige un token válido.
	 *
	 * @param http objeto que permite configurar la seguridad web
	 * @return la cadena de filtros de seguridad ya configurada
	 * @throws Exception si ocurre un error al construir la configuración
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
				.sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/autenticacion/**", "/api/usuarios/registro", "/", "/swagger-ui/**",
								"/swagger-ui.html", "/v3/api-docs/**")
						.permitAll().requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().authenticated())
				.exceptionHandling(
						error -> error.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * Define qué aplicaciones web pueden llamar a la API desde el navegador. Por
	 * ahora solo se permite el servidor de desarrollo de Angular.
	 *
	 * @return la configuración de CORS aplicada a todas las rutas de la API
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuracion = new CorsConfiguration();
		configuracion.setAllowedOrigins(List.of("https://proyectoclubpenguin.netlify.app"));
		configuracion.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuracion.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		configuracion.setExposedHeaders(List.of("Content-Disposition"));

		UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
		fuente.registerCorsConfiguration("/api/**", configuracion);
		return fuente;
	}

}
