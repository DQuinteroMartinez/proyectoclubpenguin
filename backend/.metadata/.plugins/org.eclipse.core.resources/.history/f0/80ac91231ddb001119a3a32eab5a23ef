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
/**
 * Configuración de seguridad de la aplicación. Por ahora solo define el
 * cifrador de contraseñas y deja las rutas abiertas. Más adelante se
 * reemplazará por la protección con JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
     * Define las reglas de acceso a las rutas de la API.
     * El registro, el login y Swagger son públicos; todo lo demás exige un token válido.
     *
     * @param http objeto que permite configurar la seguridad web
     * @return la cadena de filtros de seguridad ya configurada
     * @throws Exception si ocurre un error al construir la configuración
     */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers(
		        "/api/autenticacion/**",
		        "/api/usuarios/registro",
		        "/",
		        "/swagger-ui/**",
		        "/swagger-ui.html",
		        "/v3/api-docs/**").permitAll()
				.anyRequest().authenticated())
        .exceptionHandling(error -> error
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
