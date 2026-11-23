package co.edu.unbosque.clubpenguin.dto;

/**
 * Representa una fila del ranking de un minijuego: la posición, el usuario y su
 * mejor puntaje.
 */

public class RankingDto {
	/** Posición del usuario en el ranking (1 es el primer lugar). */
	private int posicion;

	/** Nombre del usuario. */
	private String nombreUsuario;

	/** Mejor puntaje que el usuario ha obtenido en el minijuego. */
	private Integer mejorPuntaje;

	/**
	 * Constructor vacío.
	 */
	public RankingDto() {
	}

	/**
	 * Crea una fila del ranking sin posición. La posición se asigna después en el
	 * service. Este constructor lo usa la consulta del repositorio.
	 *
	 * @param nombreUsuario nombre del usuario
	 * @param mejorPuntaje  mejor puntaje del usuario en el minijuego
	 */
	public RankingDto(String nombreUsuario, Integer mejorPuntaje) {
		this.nombreUsuario = nombreUsuario;
		this.mejorPuntaje = mejorPuntaje;
	}

	/**
	 * Obtiene la posición en el ranking.
	 *
	 * @return la posición del usuario
	 */
	public int getPosicion() {
		return posicion;
	}

	/**
	 * Cambia la posición en el ranking.
	 *
	 * @param posicion nueva posición
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/**
	 * Obtiene el nombre del usuario.
	 *
	 * @return el nombre de usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Cambia el nombre del usuario.
	 *
	 * @param nombreUsuario nuevo nombre de usuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Obtiene el mejor puntaje del usuario.
	 *
	 * @return el mejor puntaje en el minijuego
	 */
	public Integer getMejorPuntaje() {
		return mejorPuntaje;
	}

	/**
	 * Cambia el mejor puntaje del usuario.
	 *
	 * @param mejorPuntaje nuevo mejor puntaje
	 */
	public void setMejorPuntaje(Integer mejorPuntaje) {
		this.mejorPuntaje = mejorPuntaje;
	}

}
