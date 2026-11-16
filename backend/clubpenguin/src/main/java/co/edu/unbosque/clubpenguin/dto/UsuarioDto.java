package co.edu.unbosque.clubpenguin.dto;

import java.time.LocalDateTime;

/**
 * Datos de un usuario que se pueden mostrar al cliente. No incluye la
 * contraseña, para no exponerla nunca.
 */
public class UsuarioDto {

	/** Identificador del usuario. */
	private Long id;

	/** Nombre de usuario. */
	private String nombreUsuario;

	/** Correo electrónico del usuario. */
	private String correo;

	/** Nombre del rol del usuario (ADMIN o USUARIO). */
	private String rol;

	/** Fecha y hora en la que se creó la cuenta. */
	private LocalDateTime fechaRegistro;

	/**
	 * Constructor vacío.
	 */
	public UsuarioDto() {
	}

	/**
	 * Obtiene el identificador del usuario.
	 *
	 * @return el id del usuario
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Cambia el identificador del usuario.
	 *
	 * @param id nuevo id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de usuario.
	 *
	 * @return el nombre de usuario
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
	 * @return el correo del usuario
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
	 * Obtiene el nombre del rol.
	 *
	 * @return el nombre del rol del usuario
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Cambia el nombre del rol.
	 *
	 * @param rol nuevo nombre de rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene la fecha de registro.
	 *
	 * @return la fecha y hora de creación de la cuenta
	 */
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Cambia la fecha de registro.
	 *
	 * @param fechaRegistro nueva fecha de registro
	 */
	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}