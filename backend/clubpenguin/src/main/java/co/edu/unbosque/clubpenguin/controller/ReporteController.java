package co.edu.unbosque.clubpenguin.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.service.ReportePdfService;

/**
 * Expone los endpoints REST para descargar reportes en formato PDF.
 */
@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

	/** Service que genera los reportes en PDF. */
	private final ReportePdfService reportePdfService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param reportePdfService service de reportes en PDF
	 */
	public ReporteController(ReportePdfService reportePdfService) {
		this.reportePdfService = reportePdfService;
	}

	/**
	 * Descarga el ranking de un minijuego en formato PDF.
	 *
	 * @param minijuegoId identificador del minijuego, tomado de la ruta
	 * @return una respuesta 200 con el archivo PDF como descarga
	 */
	@GetMapping("/ranking/{minijuegoId}")
	public ResponseEntity<byte[]> descargarRanking(@PathVariable Long minijuegoId) {
		byte[] pdf = reportePdfService.generarRankingPdf(minijuegoId);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ranking-" + minijuegoId + ".pdf")
				.contentType(MediaType.APPLICATION_PDF).body(pdf);
	}

}
