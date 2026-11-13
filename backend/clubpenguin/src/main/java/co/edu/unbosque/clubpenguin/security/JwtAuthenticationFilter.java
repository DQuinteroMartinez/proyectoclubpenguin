package co.edu.unbosque.clubpenguin.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que se ejecuta en cada petición.
 * Si la petición trae un token JWT válido, identifica al usuario y lo deja pasar.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Utilidad para verificar y leer tokens. */
    private final JwtUtil jwtUtil;

    /** Service para cargar los datos de seguridad del usuario. */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Crea el filtro. Spring entrega automáticamente las dependencias.
     *
     * @param jwtUtil utilidad de tokens JWT
     * @param userDetailsService service que carga usuarios para Spring Security
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Revisa el encabezado Authorization de la petición. Si contiene un token válido,
     * registra al usuario como autenticado y continúa con la petición.
     *
     * @param request petición HTTP recibida
     * @param response respuesta HTTP que se enviará
     * @param filterChain cadena de filtros que sigue después de este
     * @throws ServletException si ocurre un error al procesar la petición
     * @throws IOException si ocurre un error de lectura o escritura
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String encabezado = request.getHeader("Authorization");

        if (encabezado != null && encabezado.startsWith("Bearer ")) {
            String token = encabezado.substring(7);

            if (jwtUtil.esValido(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                String nombreUsuario = jwtUtil.extraerNombreUsuario(token);
                UserDetails detalles = userDetailsService.loadUserByUsername(nombreUsuario);

                UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                        detalles, null, detalles.getAuthorities());
                autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        }

        filterChain.doFilter(request, response);
    }
}
