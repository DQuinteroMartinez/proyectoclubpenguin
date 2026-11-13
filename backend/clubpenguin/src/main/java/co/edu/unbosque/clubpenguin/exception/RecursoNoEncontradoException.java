package co.edu.unbosque.clubpenguin.exception;

/**
 * Se lanza cuando se busca un dato que no existe en la base de datos,
 * por ejemplo un usuario o un rol con un id o nombre inexistente.
 */
public class RecursoNoEncontradoException extends RuntimeException{

	/**
	 * Identificador de versión de la clase, requerido por Java para las excepciones.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Crea la excepción con un mensaje que explica el motivo.
     *
     * @param mensaje descripción del error
     */
    public RecursoNoEncontradoException(String mensaje) {
        super("Recurso no encontrado.");
    }
}
