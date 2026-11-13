package co.edu.unbosque.clubpenguin.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Utilidad para crear y verificar tokens JWT.
 * Usa la clave secreta y la duración definidas en application.properties.
 */
@Component
public class JwtUtil {

    /** Clave secreta con la que se firman los tokens. */
    @Value("${jwt.secret}")
    private String secreto;

    /** Tiempo de vida del token, en milisegundos. */
    @Value("${jwt.expiracion}")
    private long expiracion;

    /**
     * Construye la clave criptográfica a partir del texto secreto.
     *
     * @return la clave usada para firmar y verificar tokens
     */
    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un token firmado para un usuario.
     *
     * @param nombreUsuario nombre del usuario al que pertenece el token
     * @param rol rol del usuario, que se guarda dentro del token
     * @return el token JWT como texto
     */
    public String generarToken(String nombreUsuario, String rol) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(nombreUsuario)
                .claim("rol", rol)
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiracion))
                .signWith(obtenerClave())
                .compact();
    }

    /**
     * Obtiene el nombre de usuario guardado dentro de un token.
     *
     * @param token token JWT del que se quiere leer el nombre
     * @return el nombre de usuario del token
     */
    public String extraerNombreUsuario(String token) {
        return extraerClaims(token).getSubject();
    }

    /**
     * Verifica que un token tenga una firma correcta y no haya expirado.
     *
     * @param token token JWT que se quiere verificar
     * @return true si el token es válido, false si está alterado, vencido o mal formado
     */
    public boolean esValido(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Lee el contenido del token comprobando su firma.
     *
     * @param token token JWT que se quiere abrir
     * @return los datos (claims) guardados en el token
     */
    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
