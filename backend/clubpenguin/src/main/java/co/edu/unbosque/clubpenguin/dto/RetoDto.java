package co.edu.unbosque.clubpenguin.dto;

import java.time.LocalDateTime;

/**
 * Datos de un reto que se pueden mostrar al cliente.
 */

public class RetoDto {

	/** Identificador del reto. */
	private Long id;

	/** Nombre del jugador que lanzó el reto. */
	private String retador;

	/** Nombre del jugador que recibió el reto. */
	private String retado;

	/** Nombre del minijuego del reto. */
	private String minijuego;

	/** Puntaje que el jugador retado debe superar. */
	private Integer puntajeObjetivo;

	/**
	 * Puntaje que obtuvo el jugador retado (vacío mientras el reto esté pendiente).
	 */
	private Integer puntajeRetado;

	/** Estado del reto (PENDIENTE, SUPERADO o NO_SUPERADO). */
	private String estado;

	/** Fecha y hora en la que se creó el reto. */
	private LocalDateTime fechaCreacion;

	/**
	 * Fecha y hora en la que se resolvió el reto (vacía mientras esté pendiente).
	 */
	private LocalDateTime fechaResolucion;

	/**
	 * Constructor vacío.
	 */
	public RetoDto() {
	}

	/**
	 * Obtiene el identificador del reto.
	 *
	 * @return el id del reto
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Cambia el identificador del reto.
	 *
	 * @param id nuevo id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del retador.
	 *
	 * @return el nombre del jugador que lanzó el reto
	 */
	public String getRetador() {
		return retador;
	}

	/**
	 * Cambia el nombre del retador.
	 *
	 * @param retador nuevo nombre del retador
	 */
	public void setRetador(String retador) {
		this.retador = retador;
	}

	/**
	 * Obtiene el nombre del retado.
	 *
	 * @return el nombre del jugador que recibió el reto
	 */
	public String getRetado() {
		return retado;
	}

	/**
	 * Cambia el nombre del retado.
	 *
	 * @param retado nuevo nombre del retado
	 */
	public void setRetado(String retado) {
		this.retado = retado;
	}

	/**
	 * Obtiene el nombre del minijuego.
	 *
	 * @return el nombre del minijuego del reto
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
	 * Obtiene el puntaje que se debe superar.
	 *
	 * @return el puntaje objetivo
	 */
	public Integer getPuntajeObjetivo() {
		return puntajeObjetivo;
	}

	/**
	 * Cambia el puntaje que se debe superar.
	 *
	 * @param puntajeObjetivo nuevo puntaje objetivo
	 */
	public void setPuntajeObjetivo(Integer puntajeObjetivo) {
		this.puntajeObjetivo = puntajeObjetivo;
	}

	/**
	 * Obtiene el puntaje del jugador retado.
	 *
	 * @return el puntaje del retado, o null si el reto sigue pendiente
	 */
	public Integer getPuntajeRetado() {
		return puntajeRetado;
	}

	/**
	 * Cambia el puntaje del jugador retado.
	 *
	 * @param puntajeRetado nuevo puntaje del retado
	 */
	public void setPuntajeRetado(Integer puntajeRetado) {
		this.puntajeRetado = puntajeRetado;
	}

	/**
	 * Obtiene el estado del reto.
	 *
	 * @return el estado como texto
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Cambia el estado del reto.
	 *
	 * @param estado nuevo estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene la fecha de creación del reto.
	 *
	 * @return la fecha y hora de creación
	 */
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Cambia la fecha de creación del reto.
	 *
	 * @param fechaCreacion nueva fecha de creación
	 */
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Obtiene la fecha de resolución del reto.
	 *
	 * @return la fecha y hora de resolución, o null si el reto sigue pendiente
	 */
	public LocalDateTime getFechaResolucion() {
		return fechaResolucion;
	}

	/**
	 * Cambia la fecha de resolución del reto.
	 *
	 * @param fechaResolucion nueva fecha de resolución
	 */
	public void setFechaResolucion(LocalDateTime fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

}
