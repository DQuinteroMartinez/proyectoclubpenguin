package co.edu.unbosque.clubpenguin.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa un reto que un jugador le hace a otro en un minijuego. Cada objeto
 * de esta clase corresponde a una fila de la tabla "retos".
 */
@Entity
@Table(name = "retos")
public class Reto {

	/**
	 * Identificador único del reto, generado automáticamente por la base de datos.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Jugador que lanza el reto. */
	@ManyToOne
	@JoinColumn(name = "retador_id", nullable = false)
	private Usuario retador;

	/** Jugador que recibe el reto. */
	@ManyToOne
	@JoinColumn(name = "retado_id", nullable = false)
	private Usuario retado;

	/** Minijuego en el que se juega el reto. */
	@ManyToOne
	@JoinColumn(name = "minijuego_id", nullable = false)
	private Minijuego minijuego;

	/** Puntaje que el jugador retado debe superar. */
	@Column(nullable = false)
	private Integer puntajeObjetivo;

	/**
	 * Puntaje que obtuvo el jugador retado al responder el reto (vacío mientras
	 * esté pendiente).
	 */
	private Integer puntajeRetado;

	/** Estado actual del reto. */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoReto estado;

	/** Fecha y hora en la que se creó el reto. */
	@Column(nullable = false)
	private LocalDateTime fechaCreacion;

	/**
	 * Fecha y hora en la que se resolvió el reto (vacía mientras esté pendiente).
	 */
	private LocalDateTime fechaResolucion;

	/**
	 * Constructor vacío, necesario para que JPA pueda crear objetos al leer los
	 * datos desde la base de datos.
	 */
	public Reto() {
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
	 * @param id nuevo id del reto
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el jugador que lanzó el reto.
	 *
	 * @return el retador
	 */
	public Usuario getRetador() {
		return retador;
	}

	/**
	 * Cambia el jugador que lanzó el reto.
	 *
	 * @param retador nuevo retador
	 */
	public void setRetador(Usuario retador) {
		this.retador = retador;
	}

	/**
	 * Obtiene el jugador que recibió el reto.
	 *
	 * @return el retado
	 */
	public Usuario getRetado() {
		return retado;
	}

	/**
	 * Cambia el jugador que recibió el reto.
	 *
	 * @param retado nuevo retado
	 */
	public void setRetado(Usuario retado) {
		this.retado = retado;
	}

	/**
	 * Obtiene el minijuego del reto.
	 *
	 * @return el minijuego en el que se juega el reto
	 */
	public Minijuego getMinijuego() {
		return minijuego;
	}

	/**
	 * Cambia el minijuego del reto.
	 *
	 * @param minijuego nuevo minijuego
	 */
	public void setMinijuego(Minijuego minijuego) {
		this.minijuego = minijuego;
	}

	/**
	 * Obtiene el puntaje que se debe superar.
	 *
	 * @return el puntaje objetivo del reto
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
	 * Obtiene el puntaje que obtuvo el jugador retado.
	 *
	 * @return el puntaje del retado, o null si el reto sigue pendiente
	 */
	public Integer getPuntajeRetado() {
		return puntajeRetado;
	}

	/**
	 * Cambia el puntaje que obtuvo el jugador retado.
	 *
	 * @param puntajeRetado nuevo puntaje del retado
	 */
	public void setPuntajeRetado(Integer puntajeRetado) {
		this.puntajeRetado = puntajeRetado;
	}

	/**
	 * Obtiene el estado del reto.
	 *
	 * @return el estado actual del reto
	 */
	public EstadoReto getEstado() {
		return estado;
	}

	/**
	 * Cambia el estado del reto.
	 *
	 * @param estado nuevo estado
	 */
	public void setEstado(EstadoReto estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene la fecha y hora de creación del reto.
	 *
	 * @return la fecha y hora en la que se creó el reto
	 */
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Cambia la fecha y hora de creación del reto.
	 *
	 * @param fechaCreacion nueva fecha de creación
	 */
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Obtiene la fecha y hora en la que se resolvió el reto.
	 *
	 * @return la fecha de resolución, o null si el reto sigue pendiente
	 */
	public LocalDateTime getFechaResolucion() {
		return fechaResolucion;
	}

	/**
	 * Cambia la fecha y hora de resolución del reto.
	 *
	 * @param fechaResolucion nueva fecha de resolución
	 */
	public void setFechaResolucion(LocalDateTime fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

}
