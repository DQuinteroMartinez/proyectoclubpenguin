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

import co.edu.unbosque.clubpenguin.dto.NuevaPartidaDto;
import co.edu.unbosque.clubpenguin.dto.PartidaDto;
import co.edu.unbosque.clubpenguin.service.PartidaService;
import jakarta.validation.Valid;

/**
 * Expone los endpoints REST para registrar partidas y consultar el historial
 * propio. Siempre se trabaja con el usuario que envía el token.
 */
@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

	/** Service con la lógica de negocio de las partidas. */
	private final PartidaService partidaService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param partidaService service de partidas
	 */
	public PartidaController(PartidaService partidaService) {
		this.partidaService = partidaService;
	}

	/**
	 * Registra una partida terminada para el usuario que hace la petición.
	 *
	 * @param nuevaPartidaDto minijuego jugado y puntaje, enviados en el cuerpo de
	 *                        la petición
	 * @param autenticacion   datos del usuario identificado por el token
	 * @return una respuesta 201 (creado) con los datos de la partida guardada
	 */
	@PostMapping
	public ResponseEntity<PartidaDto> registrar(@Valid @RequestBody NuevaPartidaDto nuevaPartidaDto,
			Authentication autenticacion) {
		PartidaDto creada = partidaService.registrar(autenticacion.getName(), nuevaPartidaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(creada);
	}

	/**
	 * Consulta el historial de partidas del usuario que hace la petición.
	 *
	 * @param autenticacion datos del usuario identificado por el token
	 * @return una respuesta 200 con la lista de partidas del usuario
	 */
	@GetMapping("/mias")
	public ResponseEntity<List<PartidaDto>> historial(Authentication autenticacion) {
		return ResponseEntity.ok(partidaService.historial(autenticacion.getName()));
	}
}
