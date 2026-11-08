package co.edu.unbosque.clubpenguin.dto;
/**
 * Respuesta que recibe la persona cuando inicia sesión correctamente.
 * Contiene el token que debe enviar en las siguientes peticiones.
 */
public class TokenDto {

    /** Token JWT firmado por el servidor. */
    private String token;

    /** Nombre del usuario que inició sesión. */
    private String nombreUsuario;

    /** Rol del usuario (ADMIN o USUARIO). */
    private String rol;

    /**
     * Constructor vacío.
     */
    public TokenDto() {
    }

    /**
     * Crea la respuesta con todos sus datos.
     *
     * @param token token JWT generado
     * @param nombreUsuario nombre del usuario autenticado
     * @param rol rol del usuario autenticado
     */
    public TokenDto(String token, String nombreUsuario, String rol) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    /**
     * Obtiene el token JWT.
     *
     * @return el token generado
     */
    public String getToken() {
        return token;
    }

    /**
     * Cambia el token JWT.
     *
     * @param token nuevo token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Obtiene el nombre del usuario autenticado.
     *
     * @return el nombre de usuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Cambia el nombre del usuario autenticado.
     *
     * @param nombreUsuario nuevo nombre de usuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el rol del usuario autenticado.
     *
     * @return el nombre del rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Cambia el rol del usuario autenticado.
     *
     * @param rol nuevo nombre de rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

}
