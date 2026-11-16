package co.edu.unbosque.clubpenguin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.dto.PosicionDto;
import co.edu.unbosque.clubpenguin.service.PosicionService;
import jakarta.validation.Valid;

/**
 * Expone los endpoints REST para guardar y consultar la posición del jugador en
 * el mapa. Siempre se trabaja con el usuario que envía el token.
 */
@RestController
@RequestMapping("/api/posicion")

public class PosicionController {

	/** Service con la lógica de negocio de la posición. */
	private final PosicionService posicionService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param posicionService service de posición
	 */
	public PosicionController(PosicionService posicionService) {
		this.posicionService = posicionService;
	}

	/**
	 * Consulta la última posición guardada del jugador que hace la petición.
	 *
	 * @param autenticacion datos del usuario identificado por el token
	 * @return una respuesta 200 con las coordenadas del jugador
	 */
	@GetMapping
	public ResponseEntity<PosicionDto> obtener(Authentication autenticacion) {
		return ResponseEntity.ok(posicionService.obtener(autenticacion.getName()));
	}

	/**
	 * Guarda la nueva posición del jugador que hace la petición.
	 *
	 * @param posicionDto   nuevas coordenadas, enviadas en el cuerpo de la petición
	 * @param autenticacion datos del usuario identificado por el token
	 * @return una respuesta 200 con la posición que quedó guardada
	 */
	@PutMapping
	public ResponseEntity<PosicionDto> actualizar(@Valid @RequestBody PosicionDto posicionDto,
			Authentication autenticacion) {
		return ResponseEntity.ok(posicionService.actualizar(autenticacion.getName(), posicionDto));
	}

}
