package co.edu.unbosque.clubpenguin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.dto.CambioRolDto;
import co.edu.unbosque.clubpenguin.dto.UsuarioDto;
import co.edu.unbosque.clubpenguin.service.UsuarioService;
import jakarta.validation.Valid;

/**
 * Expone los endpoints reservados para el administrador. Todas las rutas que
 * empiezan por /api/admin exigen el rol ADMIN.
 */
@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminController {

	/** Service con la lógica de negocio de los usuarios. */
	private final UsuarioService usuarioService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param usuarioService service de usuarios
	 */
	public AdminController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * Cambia el rol de un usuario.
	 *
	 * @param id           identificador del usuario, tomado de la ruta
	 * @param cambioRolDto nuevo rol enviado en el cuerpo de la petición
	 * @return una respuesta 200 con los datos del usuario actualizado
	 */
	@PutMapping("/{id}/rol")
	public ResponseEntity<UsuarioDto> cambiarRol(@PathVariable Long id, @Valid @RequestBody CambioRolDto cambioRolDto) {
		return ResponseEntity.ok(usuarioService.cambiarRol(id, cambioRolDto.getRol()));
	}

	/**
	 * Elimina la cuenta de un usuario.
	 *
	 * @param id identificador del usuario, tomado de la ruta
	 * @return una respuesta 204 (sin contenido) si se eliminó correctamente
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		usuarioService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
