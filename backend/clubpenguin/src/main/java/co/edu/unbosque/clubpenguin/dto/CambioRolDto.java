package co.edu.unbosque.clubpenguin.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos que envía un administrador para cambiar el rol de un usuario.
 */
public class CambioRolDto {

    /** Nombre del nuevo rol (ADMIN o USUARIO). */
    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    /**
     * Constructor vacío, necesario para que Spring convierta el JSON recibido en un objeto.
     */
    public CambioRolDto() {
    }

    /**
     * Obtiene el nombre del nuevo rol.
     *
     * @return el nombre del rol enviado
     */
    public String getRol() {
        return rol;
    }

    /**
     * Cambia el nombre del nuevo rol.
     *
     * @param rol nuevo nombre de rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

}
