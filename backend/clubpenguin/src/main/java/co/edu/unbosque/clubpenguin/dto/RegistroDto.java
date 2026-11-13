package co.edu.unbosque.clubpenguin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos que envía una persona para crear su cuenta. Incluye validaciones para
 * rechazar información incompleta o inválida.
 */
public class RegistroDto {

	/** Nombre de usuario elegido por la persona. */
	@NotBlank(message = "El nombre de usuario es obligatorio")
	@Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
	private String nombreUsuario;

	/** Correo electrónico de la persona. */
	@NotBlank(message = "El correo es obligatorio")
	@Email(message = "El correo no tiene un formato válido")
	private String correo;

	/** Contraseña elegida, todavía sin cifrar. */
	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
	private String contrasena;

	/**
	 * Constructor vacío, necesario para que Spring convierta el JSON recibido en un
	 * objeto.
	 */
	public RegistroDto() {
	}

	/**
	 * Obtiene el nombre de usuario.
	 *
	 * @return el nombre de usuario elegido
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Cambia el nombre de usuario.
	 *
	 * @param nombreUsuario nuevo nombre de usuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Obtiene el correo electrónico.
	 *
	 * @return el correo de la persona
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Cambia el correo electrónico.
	 *
	 * @param correo nuevo correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contraseña sin cifrar.
	 *
	 * @return la contraseña enviada
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Cambia la contraseña.
	 *
	 * @param contrasena nueva contraseña
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
}