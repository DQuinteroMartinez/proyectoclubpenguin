package co.edu.unbosque.clubpenguin.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa una partida jugada por un usuario en un minijuego. Cada objeto de
 * esta clase corresponde a una fila de la tabla "partidas", y el conjunto de
 * partidas forma el historial y los rankings.
 */
@Entity
@Table(name = "partidas")
public class Partida {

	/**
	 * Identificador único de la partida, generado automáticamente por la base de
	 * datos.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Usuario que jugó la partida. Un usuario puede tener muchas partidas. */
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	/**
	 * Minijuego en el que se jugó la partida. Un minijuego puede tener muchas
	 * partidas.
	 */
	@ManyToOne
	@JoinColumn(name = "minijuego_id", nullable = false)
	private Minijuego minijuego;

	/** Puntaje obtenido en la partida. */
	@Column(nullable = false)
	private Integer puntaje;

	/** Fecha y hora en la que se registró la partida. */
	@Column(nullable = false)
	private LocalDateTime fecha;

	/**
	 * Constructor vacío, necesario para que JPA pueda crear objetos al leer los
	 * datos desde la base de datos.
	 */
	public Partida() {
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
	 * @param id nuevo id de la partida
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el usuario que jugó la partida.
	 *
	 * @return el usuario de la partida
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Cambia el usuario que jugó la partida.
	 *
	 * @param usuario nuevo usuario de la partida
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el minijuego en el que se jugó la partida.
	 *
	 * @return el minijuego de la partida
	 */
	public Minijuego getMinijuego() {
		return minijuego;
	}

	/**
	 * Cambia el minijuego de la partida.
	 *
	 * @param minijuego nuevo minijuego de la partida
	 */
	public void setMinijuego(Minijuego minijuego) {
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
	 * @return la fecha y hora en la que se registró la partida
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
