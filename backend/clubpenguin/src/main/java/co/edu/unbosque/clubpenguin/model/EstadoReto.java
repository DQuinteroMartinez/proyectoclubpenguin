package co.edu.unbosque.clubpenguin.model;

/**
 * Estados posibles de un reto entre jugadores.
 */

public enum EstadoReto {

	/** El reto fue enviado y el jugador retado todavía no ha jugado. */
	PENDIENTE,

	/** El jugador retado superó el puntaje del reto. */
	SUPERADO,

	/** El jugador retado jugó pero no superó el puntaje del reto. */
	NO_SUPERADO

}
