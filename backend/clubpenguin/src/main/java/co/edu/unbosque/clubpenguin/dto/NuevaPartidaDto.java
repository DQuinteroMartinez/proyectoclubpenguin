package co.edu.unbosque.clubpenguin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Datos que envía el juego para registrar una partida terminada. El usuario no
 * se envía: se toma del token de quien hace la petición.
 */
public class NuevaPartidaDto {

	/** Identificador del minijuego que se jugó. */
	@NotNull(message = "El minijuego es obligatorio")
	private Long minijuegoId;

	/** Puntaje obtenido en la partida. */
	@NotNull(message = "El puntaje es obligatorio")
	@Min(value = 0, message = "El puntaje no puede ser negativo")
	private Integer puntaje;

	/**
	 * Constructor vacío, necesario para que Spring convierta el JSON recibido en un
	 * objeto.
	 */
	public NuevaPartidaDto() {
	}

	/**
	 * Obtiene el identificador del minijuego.
	 *
	 * @return el id del minijuego jugado
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

	/**
	 * Obtiene el puntaje de la partida.
	 *
	 * @return el puntaje obtenido
	 */
	public Integer getPuntaje() {
		return puntaje;
	}

	/**
	 * Cambia el puntaje de la partida.
	 *
	 * @param puntaje nuevo puntaje
	 */
	public void setPuntaje(Integer puntaje) {
		this.puntaje = puntaje;
	}

}
