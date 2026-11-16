package co.edu.unbosque.clubpenguin.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa a un usuario registrado en el juego.
 * Cada objeto de esta clase corresponde a una fila de la tabla "usuarios".
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /** Identificador único del usuario, generado automáticamente por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre con el que el usuario aparece en el juego. No puede repetirse. */
    @Column(nullable = false, unique = true)
    private String nombreUsuario;

    /** Correo electrónico del usuario. No puede repetirse. */
    @Column(nullable = false, unique = true)
    private String correo;

    /** Contraseña del usuario (más adelante se guardará cifrada). */
    @Column(nullable = false)
    private String contrasena;

    /** Fecha y hora en la que el usuario creó su cuenta. */
    private LocalDateTime fechaRegistro;

    /** Rol del usuario (ADMIN o USUARIO). Muchos usuarios pueden compartir el mismo rol. */
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    /**
     * Constructor vacío, necesario para que JPA pueda crear objetos
     * al leer los datos desde la base de datos.
     */
    public Usuario() {
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return el id del usuario
     */
    public Long getId() {
        return id;
    }

    /**
     * Cambia el identificador del usuario.
     *
     * @param id nuevo id del usuario
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return el nombre con el que el usuario aparece en el juego
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
     * Obtiene el correo electrónico del usuario.
     *
     * @return el correo del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Cambia el correo electrónico del usuario.
     *
     * @param correo nuevo correo del usuario
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Cambia la contraseña del usuario.
     *
     * @param contrasena nueva contraseña del usuario
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene la fecha y hora de registro del usuario.
     *
     * @return la fecha y hora en la que se creó la cuenta
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Cambia la fecha y hora de registro del usuario.
     *
     * @param fechaRegistro nueva fecha y hora de registro
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return el rol asignado al usuario
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Cambia el rol del usuario.
     *
     * @param rol nuevo rol del usuario
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}