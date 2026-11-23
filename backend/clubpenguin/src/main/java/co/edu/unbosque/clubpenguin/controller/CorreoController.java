package co.edu.unbosque.clubpenguin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.clubpenguin.service.CorreoService;

/**
 * Expone un endpoint para que el administrador compruebe que el envío de
 * correos funciona. Todas las rutas que empiezan por /api/admin exigen el rol
 * ADMIN.
 */
@RestController
@RequestMapping("/api/admin/correo")

public class CorreoController {

	/** Service que envía los correos. */
	private final CorreoService correoService;

	/**
	 * Crea el controller. Spring entrega automáticamente el service.
	 *
	 * @param correoService service de correos
	 */
	public CorreoController(CorreoService correoService) {
		this.correoService = correoService;
	}

	/**
	 * Envía un correo de prueba a la dirección indicada.
	 *
	 * @param destinatario dirección de correo que recibirá el mensaje de prueba
	 * @return una respuesta 200 con un mensaje de confirmación
	 */
	@PostMapping("/prueba")
	public ResponseEntity<Map<String, String>> enviarPrueba(@RequestParam String destinatario) {
		correoService.enviarCorreo(destinatario, "Prueba de Club Penguin",
				"Si lees este mensaje, el envío de correos del backend funciona correctamente.");

		Map<String, String> cuerpo = new HashMap<>();
		cuerpo.put("mensaje", "Correo de prueba enviado a " + destinatario);
		return ResponseEntity.ok(cuerpo);
	}
}
