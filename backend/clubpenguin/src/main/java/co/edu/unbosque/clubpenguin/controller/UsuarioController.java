package co.edu.unbosque.clubpenguin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.dto.RegistroDto;
import co.edu.unbosque.clubpenguin.dto.UsuarioDto;
import co.edu.unbosque.clubpenguin.service.UsuarioService;
import jakarta.validation.Valid;

/**
 * Expone los endpoints REST para registrar usuarios y consultar el listado.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    /** Service con la lógica de negocio de los usuarios. */
    private final UsuarioService usuarioService;

    /**
     * Crea el controller. Spring entrega automáticamente el service.
     *
     * @param usuarioService service de usuarios
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Registra una cuenta nueva.
     *
     * @param registroDto datos del registro enviados en el cuerpo de la petición
     * @return una respuesta 201 (creado) con los datos del usuario nuevo
     */
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registrar(@Valid @RequestBody RegistroDto registroDto) {
        UsuarioDto creado = usuarioService.registrar(registroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * Consulta el listado de usuarios que tienen una cuenta creada.
     *
     * @return una respuesta 200 con la lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

}
