package co.edu.unbosque.clubpenguin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datos que envía un jugador para retar a otro. El retador no se envía: se toma
 * del token de quien hace la petición.
 */

public class NuevoRetoDto {

	/** Nombre de usuario del jugador al que se quiere retar. */
	@NotBlank(message = "El nombre del usuario retado es obligatorio")
	private String nombreUsuarioRetado;

	/** Identificador del minijuego en el que se hace el reto. */
	@NotNull(message = "El minijuego es obligatorio")
	private Long minijuegoId;

	/**
	 * Constructor vacío, necesario para que Spring convierta el JSON recibido en un
	 * objeto.
	 */
	public NuevoRetoDto() {
	}

	/**
	 * Obtiene el nombre del usuario retado.
	 *
	 * @return el nombre de usuario del retado
	 */
	public String getNombreUsuarioRetado() {
		return nombreUsuarioRetado;
	}

	/**
	 * Cambia el nombre del usuario retado.
	 *
	 * @param nombreUsuarioRetado nuevo nombre de usuario del retado
	 */
	public void setNombreUsuarioRetado(String nombreUsuarioRetado) {
		this.nombreUsuarioRetado = nombreUsuarioRetado;
	}

	/**
	 * Obtiene el identificador del minijuego.
	 *
	 * @return el id del minijuego del reto
	 */
	public Long getMinijuegoId() {
		return minijuegoId;
	}

	/**
	 * Cambia el identificador del minijuego.
	 *
	 * @param minijuegoId nuevo id del minijuego
	 */
	public void setMinijuegoId(Long minijuegoId) {
		this.minijuegoId = minijuegoId;
	}

}
