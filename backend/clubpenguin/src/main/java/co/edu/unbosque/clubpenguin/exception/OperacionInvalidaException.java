package co.edu.unbosque.clubpenguin.exception;

/**
 * Se lanza cuando una petición es válida en su formato pero no cumple las
 * reglas del negocio, por ejemplo, cuando un jugador intenta retarse a sí
 * mismo.
 */
public class OperacionInvalidaException extends RuntimeException {
	/**
	 * Identificador de versión de la clase, requerido por Java para las
	 * excepciones.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepción con un mensaje que explica el motivo.
	 *
	 * @param mensaje descripción del error (por ejemplo, "No puedes retarte a ti
	 *                mismo")
	 */
	public OperacionInvalidaException(String mensaje) {
		super(mensaje);
	}

}
