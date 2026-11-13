package co.edu.unbosque.clubpenguin.dto;
import jakarta.validation.constraints.NotBlank;

/**
 * Datos que envía una persona para iniciar sesión.
 */
public class LoginDto {

    /** Nombre de usuario con el que se registró la persona. */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    /** Contraseña de la persona, sin cifrar. */
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    /**
     * Constructor vacío, necesario para que Spring convierta el JSON recibido en un objeto.
     */
    public LoginDto() {
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return el nombre de usuario enviado
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Cambia el nombre de usuario.
     *
     * @param nombreUsuario nuevo nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña sin cifrar.
     *
     * @return la contraseña enviada
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Cambia la contraseña.
     *
     * @param contrasena nueva contraseña
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
