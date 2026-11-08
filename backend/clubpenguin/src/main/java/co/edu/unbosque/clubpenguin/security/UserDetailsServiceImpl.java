package co.edu.unbosque.clubpenguin.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;

/**
 * Le enseña a Spring Security cómo buscar un usuario en nuestra base de datos.
 * Spring usa esta clase para verificar el login y para identificar a quien envía un token.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /** Repositorio para buscar usuarios. */
    private final UsuarioRepository usuarioRepository;

    /**
     * Crea el service. Spring entrega automáticamente el repositorio.
     *
     * @param usuarioRepository repositorio de usuarios
     */
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Busca un usuario por su nombre y lo convierte al formato que entiende Spring Security.
     *
     * @param nombreUsuario nombre del usuario que se quiere cargar
     * @return los datos de seguridad del usuario (nombre, contraseña cifrada y rol)
     * @throws UsernameNotFoundException si no existe un usuario con ese nombre
     */
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(usuario.getNombreUsuario())
                .password(usuario.getContrasena())
                .roles(usuario.getRol().getNombre())
                .build();
    }

}
