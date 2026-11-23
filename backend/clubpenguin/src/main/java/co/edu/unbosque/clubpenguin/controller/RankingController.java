package co.edu.unbosque.clubpenguin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.dto.RankingDto;
import co.edu.unbosque.clubpenguin.service.RankingService;

/**
 * Expone el endpoint REST para consultar el ranking de cada minijuego.
 */
@RestController
@RequestMapping("/api/rankings")

public class RankingController {

	/** Service con la lógica de negocio de los rankings. */
	private final RankingService rankingService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param rankingService service de rankings
	 */
	public RankingController(RankingService rankingService) {
		this.rankingService = rankingService;
	}

	/**
	 * Consulta el ranking de un minijuego.
	 *
	 * @param minijuegoId identificador del minijuego, tomado de la ruta
	 * @return una respuesta 200 con el ranking, de la primera posición hacia abajo
	 */
	@GetMapping("/{minijuegoId}")
	public ResponseEntity<List<RankingDto>> obtenerRanking(@PathVariable Long minijuegoId) {
		return ResponseEntity.ok(rankingService.obtenerRanking(minijuegoId));
	}

}
