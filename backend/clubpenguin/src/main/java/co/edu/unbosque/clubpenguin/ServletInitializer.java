package co.edu.unbosque.clubpenguin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

 /**
 * Permite que la aplicación se despliegue como archivo WAR en un servidor
 * Tomcat externo, indicándole cuál es la clase principal de Spring Boot.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación cuando es iniciada por el servidor externo.
	 *
	 * @param application constructor de la aplicación que entrega el servidor
	 * @return el constructor configurado con la clase principal del proyecto
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClubpenguinApplication.class);
	}

}
