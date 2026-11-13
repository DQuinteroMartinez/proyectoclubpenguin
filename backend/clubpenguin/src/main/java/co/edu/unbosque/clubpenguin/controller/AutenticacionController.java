package co.edu.unbosque.clubpenguin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.dto.LoginDto;
import co.edu.unbosque.clubpenguin.dto.TokenDto;
import co.edu.unbosque.clubpenguin.service.AutenticacionService;
import jakarta.validation.Valid;

/**
 * Expone el endpoint REST para iniciar sesión.
 */
@RestController
@RequestMapping("/api/autenticacion")
public class AutenticacionController {

    /** Service con la lógica de inicio de sesión. */
    private final AutenticacionService autenticacionService;

    /**
     * Crea el controller. Spring entrega automáticamente el service.
     *
     * @param autenticacionService service de autenticación
     */
    public AutenticacionController(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    /**
     * Inicia sesión con usuario y contraseña.
     *
     * @param loginDto credenciales enviadas en el cuerpo de la petición
     * @return una respuesta 200 con el token JWT, el nombre de usuario y el rol
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(autenticacionService.login(loginDto));
    }

}
