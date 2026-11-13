package co.edu.unbosque.clubpenguin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.dto.MinijuegoDto;
import co.edu.unbosque.clubpenguin.service.MinijuegoService;

/**
 * Expone el endpoint REST para consultar los minijuegos disponibles.
 */
@RestController
@RequestMapping("/api/minijuegos")
public class MinijuegoController {

	/** Service con la lógica de negocio de los minijuegos. */
	private final MinijuegoService minijuegoService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param minijuegoService service de minijuegos
	 */
	public MinijuegoController(MinijuegoService minijuegoService) {
		this.minijuegoService = minijuegoService;
	}

	/**
	 * Consulta el listado de minijuegos disponibles.
	 *
	 * @return una respuesta 200 con la lista de minijuegos
	 */
	@GetMapping
	public ResponseEntity<List<MinijuegoDto>> listar() {
		return ResponseEntity.ok(minijuegoService.listar());
	}
}
