package co.edu.unbosque.clubpenguin.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.dto.LoginDto;
import co.edu.unbosque.clubpenguin.dto.TokenDto;
import co.edu.unbosque.clubpenguin.model.Usuario;
import co.edu.unbosque.clubpenguin.repository.UsuarioRepository;
import co.edu.unbosque.clubpenguin.security.JwtUtil;

/**
 * Contiene la lógica para iniciar sesión: verifica las credenciales y entrega un token JWT.
 */
@Service
public class AutenticacionService {

    /** Componente de Spring Security que verifica usuario y contraseña. */
    private final AuthenticationManager authenticationManager;

    /** Repositorio para consultar los datos del usuario. */
    private final UsuarioRepository usuarioRepository;

    /** Utilidad para generar el token. */
    private final JwtUtil jwtUtil;

    /**
     * Crea el service. Spring entrega automáticamente cada dependencia.
     *
     * @param authenticationManager verificador de credenciales
     * @param usuarioRepository repositorio de usuarios
     * @param jwtUtil utilidad de tokens JWT
     */
    public AutenticacionService(AuthenticationManager authenticationManager,
            UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Inicia sesión. Si el usuario y la contraseña son correctos, genera un token.
     * Si son incorrectos, Spring Security lanza una BadCredentialsException.
     *
     * @param loginDto usuario y contraseña enviados por la persona
     * @return el token junto con el nombre de usuario y su rol
     */
    public TokenDto login(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getNombreUsuario(), loginDto.getContrasena()));

        Usuario usuario = usuarioRepository.findByNombreUsuario(loginDto.getNombreUsuario())
                .orElseThrow(() -> new IllegalStateException("El usuario no existe"));

        String rol = usuario.getRol().getNombre();
        String token = jwtUtil.generarToken(usuario.getNombreUsuario(), rol);

        return new TokenDto(token, usuario.getNombreUsuario(), rol);
    }

}
