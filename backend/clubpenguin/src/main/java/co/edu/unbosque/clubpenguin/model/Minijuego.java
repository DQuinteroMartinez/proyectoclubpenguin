package co.edu.unbosque.clubpenguin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa uno de los minijuegos disponibles en el sistema. Cada objeto de
 * esta clase corresponde a una fila de la tabla "minijuegos".
 */
@Entity
@Table(name = "minijuegos")
public class Minijuego {

	/**
	 * Identificador único del minijuego, generado automáticamente por la base de
	 * datos.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre del minijuego. No puede estar vacío ni repetirse. */
	@Column(nullable = false, unique = true)
	private String nombre;

	/** Breve descripción de en qué consiste el minijuego. */
	private String descripcion;

	/**
	 * Constructor vacío, necesario para que JPA pueda crear objetos al leer los
	 * datos desde la base de datos.
	 */
	public Minijuego() {
	}

	/**
	 * Crea un minijuego con su nombre y descripción.
	 *
	 * @param nombre      nombre del minijuego
	 * @param descripcion breve descripción del minijuego
	 */
	public Minijuego(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el identificador del minijuego.
	 *
	 * @return el id del minijuego
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Cambia el identificador del minijuego.
	 *
	 * @param id nuevo id del minijuego
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del minijuego.
	 *
	 * @return el nombre del minijuego
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre del minijuego.
	 *
	 * @param nombre nuevo nombre del minijuego
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la descripción del minijuego.
	 *
	 * @return la descripción del minijuego
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Cambia la descripción del minijuego.
	 *
	 * @param descripcion nueva descripción del minijuego
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
