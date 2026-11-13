package co.edu.unbosque.clubpenguin.dto;

import java.time.LocalDateTime;

/**
 * Datos de una partida que se pueden mostrar al cliente.
 */
public class PartidaDto {

	/** Identificador de la partida. */
	private Long id;

	/** Nombre del usuario que jugó la partida. */
	private String nombreUsuario;

	/** Nombre del minijuego en el que se jugó. */
	private String minijuego;

	/** Puntaje obtenido en la partida. */
	private Integer puntaje;

	/** Fecha y hora en la que se registró la partida. */
	private LocalDateTime fecha;

	/**
	 * Constructor vacío.
	 */
	public PartidaDto() {
	}

	/**
	 * Obtiene el identificador de la partida.
	 *
	 * @return el id de la partida
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Cambia el identificador de la partida.
	 *
	 * @param id nuevo id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del usuario que jugó.
	 *
	 * @return el nombre de usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Cambia el nombre del usuario que jugó.
	 *
	 * @param nombreUsuario nuevo nombre de usuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Obtiene el nombre del minijuego.
	 *
	 * @return el nombre del minijuego jugado
	 */
	public String getMinijuego() {
		return minijuego;
	}

	/**
	 * Cambia el nombre del minijuego.
	 *
	 * @param minijuego nuevo nombre de minijuego
	 */
	public void setMinijuego(String minijuego) {
		this.minijuego = minijuego;
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

	/**
	 * Obtiene la fecha y hora de la partida.
	 *
	 * @return la fecha y hora de registro
	 */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * Cambia la fecha y hora de la partida.
	 *
	 * @param fecha nueva fecha y hora
	 */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
}
