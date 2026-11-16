package co.edu.unbosque.clubpenguin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un rol del sistema (por ejemplo, ADMIN o USUARIO). Cada objeto de
 * esta clase corresponde a una fila de la tabla "roles".
 */

@Entity
@Table(name = "roles")
public class Rol {

	/**
	 * Identificador único del rol, generado automáticamente por la base de datos.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Nombre del rol. No puede estar vacío ni repetirse. */
	@Column(nullable = false, unique = true)
	private String nombre; // ADMIN o USUARIO

	/**
	 * Constructor vacío, necesario para que JPA pueda crear objetos al leer de la
	 * base de datos.
	 */
	public Rol() {
	}

	/**
	 * Crea un rol con un nombre.
	 *
	 * @param nombre nombre del rol (por ejemplo, "ADMIN" o "USUARIO")
	 */

	public Rol(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el identificador del rol.
	 *
	 * @return el id del rol
	 */

	public Long getId() {
		return id;
	}

	/**
	 * Cambia el identificador del rol.
	 *
	 * @param id nuevo id del rol
	 */

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del rol.
	 *
	 * @return el nombre del rol
	 */

	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre del rol.
	 *
	 * @param nombre nuevo nombre del rol
	 */

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
