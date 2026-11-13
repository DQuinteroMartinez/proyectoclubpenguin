package co.edu.unbosque.clubpenguin.exeption;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Atrapa las excepciones lanzadas en cualquier controller y las convierte
 * en respuestas HTTP con un mensaje claro para el cliente.
 */
@RestControllerAdvice
public class ControlExcepciones {
	/**
     * Responde cuando el usuario o el correo ya están registrados.
     *
     * @param ex excepción lanzada por el service
     * @return una respuesta 409 (conflicto) con el mensaje del error
     */
    @ExceptionHandler(UsuarioYaExisteException.class)
    public ResponseEntity<Map<String, String>> manejarUsuarioYaExiste(UsuarioYaExisteException ex) {
        Map<String, String> cuerpo = new HashMap<>();
        cuerpo.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(cuerpo);
    }

    /**
     * Responde cuando los datos enviados no cumplen las validaciones del DTO.
     *
     * @param ex excepción con la lista de campos inválidos
     * @return una respuesta 400 (petición incorrecta) con un mensaje por cada campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }

}
