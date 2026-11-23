package co.edu.unbosque.clubpenguin.exception;

/**
 * Se lanza cuando se intenta registrar un usuario cuyo nombre o correo ya
 * existe.
 */
public class UsuarioYaExisteException extends RuntimeException{
	/**
	 * Identificador de versión de la clase, requerido por Java para las
	 * excepciones.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepción con un mensaje que explica el motivo.
	 *
	 * @param mensaje descripción del error (por ejemplo, "El correo ya está
	 *                registrado")
	 */
	public UsuarioYaExisteException(String mensaje) {
		super("Este usuario ya esta registrado");
	}

}
