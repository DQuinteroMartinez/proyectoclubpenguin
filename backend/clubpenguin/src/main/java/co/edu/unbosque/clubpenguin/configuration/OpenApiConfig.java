package co.edu.unbosque.clubpenguin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuración de la documentación Swagger de la API.
 * Agrega la información general y el botón para enviar el token JWT en las pruebas.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define la información de la API y el esquema de seguridad con token Bearer.
     *
     * @return la configuración de OpenAPI que usa Swagger
     */
    @Bean
    public OpenAPI openApi() {
        String nombreEsquema = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("API de Club Penguin")
                        .version("1.0")
                        .description("Backend del juego Club Penguin: usuarios, minijuegos, rankings y retos"))
                .addSecurityItem(new SecurityRequirement().addList(nombreEsquema))
                .components(new Components().addSecuritySchemes(nombreEsquema,
                        new SecurityScheme()
                                .name(nombreEsquema)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}