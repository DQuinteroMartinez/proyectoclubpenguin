package co.edu.unbosque.clubpenguin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.dto.NuevoRetoDto;
import co.edu.unbosque.clubpenguin.dto.RetoDto;
import co.edu.unbosque.clubpenguin.service.RetoService;
import jakarta.validation.Valid;

/**
 * Expone los endpoints REST para retar a otros jugadores y consultar el
 * historial de retos. Siempre se trabaja con el usuario que envía el token.
 */
@RestController
@RequestMapping("/api/retos")
public class RetoController {

	/** Service con la lógica de negocio de los retos. */
	private final RetoService retoService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param retoService service de retos
	 */
	public RetoController(RetoService retoService) {
		this.retoService = retoService;
	}

	/**
	 * Lanza un reto a otro jugador y le avisa por correo.
	 *
	 * @param nuevoRetoDto  jugador retado y minijuego, enviados en el cuerpo de la petición
	 * @param autenticacion datos del usuario identificado por el token
	 * @return una respuesta 201 (creado) con los datos del reto
	 */
	@PostMapping
	public ResponseEntity<RetoDto> crear(@Valid @RequestBody NuevoRetoDto nuevoRetoDto, Authentication autenticacion) {
		RetoDto creado = retoService.crear(autenticacion.getName(), nuevoRetoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	/**
	 * Consulta el historial de retos del usuario que hace la petición.
	 *
	 * @param autenticacion datos del usuario identificado por el token
	 * @return una respuesta 200 con la lista de retos lanzados y recibidos
	 */
	@GetMapping("/mios")
	public ResponseEntity<List<RetoDto>> listarMios(Authentication autenticacion) {
		return ResponseEntity.ok(retoService.listarMios(autenticacion.getName()));
	}

}
