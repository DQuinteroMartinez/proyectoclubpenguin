package co.edu.unbosque.clubpenguin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Representa la posición de un jugador en el mapa. Se usa tanto para recibir la
 * nueva posición como para devolver la guardada.
 */

public class PosicionDto {

	/** Coordenada horizontal en el mapa. No puede ser negativa. */
	@NotNull(message = "La coordenada x es obligatoria")
	@Min(value = 0, message = "La coordenada x no puede ser negativa")
	private Integer x;

	/** Coordenada vertical en el mapa. No puede ser negativa. */
	@NotNull(message = "La coordenada y es obligatoria")
	@Min(value = 0, message = "La coordenada y no puede ser negativa")
	private Integer y;

	/**
	 * Constructor vacío, necesario para que Spring convierta el JSON recibido en un
	 * objeto.
	 */
	public PosicionDto() {
	}

	/**
	 * Crea una posición con sus dos coordenadas.
	 *
	 * @param x coordenada horizontal
	 * @param y coordenada vertical
	 */
	public PosicionDto(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Obtiene la coordenada horizontal.
	 *
	 * @return la coordenada x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * Cambia la coordenada horizontal.
	 *
	 * @param x nueva coordenada x
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * Obtiene la coordenada vertical.
	 *
	 * @return la coordenada y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * Cambia la coordenada vertical.
	 *
	 * @param y nueva coordenada y
	 */
	public void setY(Integer y) {
		this.y = y;
	}

}
