package co.edu.unbosque.clubpenguin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.dto.RegistroDto;
import co.edu.unbosque.clubpenguin.dto.UsuarioDto;
import co.edu.unbosque.clubpenguin.exception.RecursoNoEncontradoException;
import co.edu.unbosque.clubpenguin.exception.UsuarioYaExisteException;
import co.edu.unbosque.clubpenguin.model.Rol;
import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.RolRepository;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;
/**
 * Contiene la lógica de negocio relacionada con los usuarios:
 * registrar cuentas nuevas y consultar el listado de usuarios.
 */
@Service
public class UsuarioService {

    /** Repositorio para guardar y consultar usuarios. */
    private final UsuarioRepository usuarioRepository;

    /** Repositorio para consultar los roles. */
    private final RolRepository rolRepository;

    /** Cifrador que protege las contraseñas antes de guardarlas. */
    private final PasswordEncoder passwordEncoder;

    /**
     * Crea el service. Spring entrega automáticamente cada dependencia.
     *
     * @param usuarioRepository repositorio de usuarios
     * @param rolRepository repositorio de roles
     * @param passwordEncoder cifrador de contraseñas
     */
    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un usuario nuevo con el rol USUARIO.
     * Verifica que el nombre y el correo no estén en uso y cifra la contraseña.
     *
     * @param registroDto datos enviados por la persona que se registra
     * @return los datos del usuario creado, sin la contraseña
     * @throws UsuarioYaExisteException si el nombre de usuario o el correo ya existen
     */
    public UsuarioDto registrar(RegistroDto registroDto) {
        if (usuarioRepository.existsByNombreUsuario(registroDto.getNombreUsuario())) {
            throw new UsuarioYaExisteException("El nombre de usuario ya está en uso");
        }
        if (usuarioRepository.existsByCorreo(registroDto.getCorreo())) {
            throw new UsuarioYaExisteException("El correo ya está registrado");
        }

        Rol rolUsuario = rolRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new IllegalStateException("El rol USUARIO no existe en la base de datos"));

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(registroDto.getNombreUsuario());
        usuario.setCorreo(registroDto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(registroDto.getContrasena()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRol(rolUsuario);

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADto(guardado);
    }

    /**
     * Obtiene el listado de todos los usuarios que tienen una cuenta creada.
     *
     * @return una lista con los datos públicos de cada usuario
     */
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte un usuario de la base de datos en un DTO seguro para mostrar.
     *
     * @param usuario usuario que se quiere convertir
     * @return el DTO con los datos públicos del usuario
     */
    private UsuarioDto convertirADto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol().getNombre());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        return dto;
    }
    
    /**
     * Cambia el rol de un usuario. Solo debe usarlo un administrador.
     *
     * @param id identificador del usuario al que se le cambiará el rol
     * @param nombreRol nombre del nuevo rol (ADMIN o USUARIO)
     * @return los datos del usuario con su nuevo rol
     * @throws RecursoNoEncontradoException si el usuario o el rol no existen
     */
    public UsuarioDto cambiarRol(Long id, String nombreRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RecursoNoEncontradoException("El rol no existe"));

        usuario.setRol(rol);
        return convertirADto(usuarioRepository.save(usuario));
    }

    /**
     * Elimina la cuenta de un usuario. Solo debe usarlo un administrador.
     *
     * @param id identificador del usuario que se eliminará
     * @throws RecursoNoEncontradoException si el usuario no existe
     */
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
    

}
