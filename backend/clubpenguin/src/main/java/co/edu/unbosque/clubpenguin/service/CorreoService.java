package co.edu.unbosque.clubpenguin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Contiene la lógica para enviar correos electrónicos desde el sistema. Se usa,
 * por ejemplo, para notificar los retos entre jugadores.
 */
@Service

public class CorreoService {

	/** Componente de Spring que se encarga de enviar los correos. */
	private final JavaMailSender mailSender;

	/** Dirección desde la que se envían los correos, tomada de la configuración. */
	@Value("${spring.mail.username}")
	private String remitente;

	/**
	 * Crea el service. Spring entrega automáticamente el componente de envío.
	 *
	 * @param mailSender componente que envía los correos
	 */
	public CorreoService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Envía un correo de texto simple.
	 *
	 * @param destinatario dirección de correo de quien lo recibe
	 * @param asunto asunto del correo
	 * @param mensaje contenido del correo
	 */
	public void enviarCorreo(String destinatario, String asunto, String mensaje) {
		SimpleMailMessage correo = new SimpleMailMessage();
		correo.setFrom(remitente);
		correo.setTo(destinatario);
		correo.setSubject(asunto);
		correo.setText(mensaje);
		mailSender.send(correo);
	}
}
