package co.edu.unbosque.clubpenguin.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.dto.RankingDto;
import co.edu.unbosque.clubpenguin.exception.RecursoNoEncontradoException;
import co.edu.unbosque.clubpenguin.model.Minijuego;
import co.edu.unbosque.clubpenguin.repository.MinijuegoRepository;

/**
 * Contiene la lógica para generar reportes en formato PDF. Por ahora genera el
 * reporte del ranking de un minijuego.
 */
@Service

public class ReportePdfService {

	/** Service que calcula el ranking que se mostrará en el reporte. */
	private final RankingService rankingService;

	/** Repositorio para obtener el nombre del minijuego. */
	private final MinijuegoRepository minijuegoRepository;

	/**
	 * Crea el service. Spring entrega automáticamente cada dependencia.
	 *
	 * @param rankingService      service de rankings
	 * @param minijuegoRepository repositorio de minijuegos
	 */
	public ReportePdfService(RankingService rankingService, MinijuegoRepository minijuegoRepository) {
		this.rankingService = rankingService;
		this.minijuegoRepository = minijuegoRepository;
	}

	/**
	 * Genera un PDF con el ranking de un minijuego.
	 *
	 * @param minijuegoId identificador del minijuego
	 * @return el contenido del archivo PDF, listo para descargar
	 * @throws RecursoNoEncontradoException si el minijuego no existe
	 */
	public byte[] generarRankingPdf(Long minijuegoId) {
		Minijuego minijuego = minijuegoRepository.findById(minijuegoId)
				.orElseThrow(() -> new RecursoNoEncontradoException("Minijuego no encontrado"));
		List<RankingDto> ranking = rankingService.obtenerRanking(minijuegoId);

		ByteArrayOutputStream salida = new ByteArrayOutputStream();
		Document documento = new Document(PageSize.A4);

		try {
			PdfWriter.getInstance(documento, salida);
			documento.open();

			Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
			Font fuenteEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
			Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

			Paragraph titulo = new Paragraph("Ranking de " + minijuego.getNombre(), fuenteTitulo);
			titulo.setAlignment(Element.ALIGN_CENTER);
			documento.add(titulo);

			String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			documento.add(new Paragraph("Generado el " + fecha, fuenteNormal));
			documento.add(new Paragraph(" "));

			if (ranking.isEmpty()) {
				documento.add(new Paragraph("Todavía no hay partidas registradas en este minijuego.", fuenteNormal));
			} else {
				PdfPTable tabla = new PdfPTable(3);
				tabla.setWidthPercentage(100);

				agregarEncabezado(tabla, "Posición", fuenteEncabezado);
				agregarEncabezado(tabla, "Usuario", fuenteEncabezado);
				agregarEncabezado(tabla, "Mejor puntaje", fuenteEncabezado);

				for (RankingDto fila : ranking) {
					tabla.addCell(new Phrase(String.valueOf(fila.getPosicion()), fuenteNormal));
					tabla.addCell(new Phrase(fila.getNombreUsuario(), fuenteNormal));
					tabla.addCell(new Phrase(String.valueOf(fila.getMejorPuntaje()), fuenteNormal));
				}

				documento.add(tabla);
			}

			documento.close();
		} catch (Exception e) {
			throw new IllegalStateException("No se pudo generar el PDF del ranking", e);
		}

		return salida.toByteArray();
	}

	/**
	 * Agrega a la tabla una celda de encabezado con fondo gris y texto centrado.
	 *
	 * @param tabla  tabla a la que se agrega la celda
	 * @param texto  texto del encabezado
	 * @param fuente fuente con la que se escribe el texto
	 */
	private void agregarEncabezado(PdfPTable tabla, String texto, Font fuente) {
		PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		tabla.addCell(celda);
	}

}
