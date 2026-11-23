package co.edu.unbosque.clubpenguin.dto;

/**
 * Datos de un minijuego que se pueden mostrar al cliente.
 */
public class MinijuegoDto {

	/** Identificador del minijuego. */
	private Long id;

	/** Nombre del minijuego. */
	private String nombre;

	/** Breve descripción del minijuego. */
	private String descripcion;

	/**
	 * Constructor vacío.
	 */
	public MinijuegoDto() {
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
	 * @param id nuevo id
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
	 * @param nombre nuevo nombre
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
	 * @param descripcion nueva descripción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
