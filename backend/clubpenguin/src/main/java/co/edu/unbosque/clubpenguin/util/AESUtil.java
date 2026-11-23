package co.edu.unbosque.clubpenguin.util;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Utilidades de seguridad: cifrado y descifrado con AES en modo GCM, y
 * funciones de resumen (hash) para comprobar la integridad de datos. La llave y
 * el IV no están escritos aquí: cada quien los entrega al llamar los métodos.
 */
public class AESUtil {

	/** Nombre del algoritmo de cifrado. */
	private static final String ALGORITMO = "AES";

	/** Modo de cifrado: AES con autenticación (GCM) y sin relleno. */
	private static final String TIPO_CIFRADO = "AES/GCM/NoPadding";

	/** Tamaño en bits de la etiqueta de autenticación de GCM. */
	private static final int TAMANO_ETIQUETA_BITS = 128;

	/**
	 * Constructor privado: esta clase solo tiene métodos estáticos y no se
	 * instancia.
	 */
	private AESUtil() {
	}

	/**
	 * Cifra un texto con AES-GCM y lo devuelve en Base64.
	 *
	 * @param llave llave secreta de 16, 24 o 32 caracteres
	 * @param iv    valor de arranque del cifrado (se recomiendan 12 caracteres)
	 * @param texto texto que se quiere cifrar
	 * @return el texto cifrado, codificado en Base64
	 * @throws IllegalStateException si la llave o el IV no son válidos o el cifrado
	 *                               falla
	 */
	public static String encrypt(String llave, String iv, String texto) {
		try {
			Cipher cipher = crearCipher(Cipher.ENCRYPT_MODE, llave, iv);
			byte[] cifrado = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(cifrado);
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException("No se pudo cifrar el texto", e);
		}
	}

	/**
	 * Descifra un texto que fue cifrado con
	 * {@link #encrypt(String, String, String)}.
	 *
	 * @param llave        la misma llave secreta usada para cifrar
	 * @param iv           el mismo IV usado para cifrar
	 * @param textoCifrado texto cifrado en Base64
	 * @return el texto original
	 * @throws IllegalStateException si la llave o el IV no coinciden, o el dato no
	 *                               está cifrado
	 */
	public static String decrypt(String llave, String iv, String textoCifrado) {
		try {
			Cipher cipher = crearCipher(Cipher.DECRYPT_MODE, llave, iv);
			byte[] cifrado = Base64.getDecoder().decode(textoCifrado);
			return new String(cipher.doFinal(cifrado), StandardCharsets.UTF_8);
		} catch (GeneralSecurityException | IllegalArgumentException e) {
			throw new IllegalStateException(
					"No se pudo descifrar el texto: revisa la llave y el IV, o que el dato esté cifrado", e);
		}
	}

	/**
	 * Calcula el hash MD5 de un texto. Está obsoleto: no usar para seguridad.
	 *
	 * @param contenido texto del que se calcula el hash
	 * @return el hash en hexadecimal
	 */
	public static String hashingToMD5(String contenido) {
		return DigestUtils.md5Hex(contenido);
	}

	/**
	 * Calcula el hash SHA-1 de un texto. Está obsoleto: no usar para seguridad.
	 *
	 * @param contenido texto del que se calcula el hash
	 * @return el hash en hexadecimal
	 */
	public static String hashingToSHA1(String contenido) {
		return DigestUtils.sha1Hex(contenido);
	}

	/**
	 * Calcula el hash SHA-256 de un texto.
	 *
	 * @param contenido texto del que se calcula el hash
	 * @return el hash en hexadecimal
	 */
	public static String hashingToSHA256(String contenido) {
		return DigestUtils.sha256Hex(contenido);
	}

	/**
	 * Calcula el hash SHA-384 de un texto.
	 *
	 * @param contenido texto del que se calcula el hash
	 * @return el hash en hexadecimal
	 */
	public static String hashingToSHA384(String contenido) {
		return DigestUtils.sha384Hex(contenido);
	}

	/**
	 * Calcula el hash SHA-512 de un texto.
	 *
	 * @param contenido texto del que se calcula el hash
	 * @return el hash en hexadecimal
	 */
	public static String hashingToSHA512(String contenido) {
		return DigestUtils.sha512Hex(contenido);
	}

	/**
	 * Prepara el objeto que realiza el cifrado o el descifrado, validando la llave
	 * y el IV.
	 *
	 * @param modo Cipher.ENCRYPT_MODE para cifrar o Cipher.DECRYPT_MODE para descifrar
	 * @param llave llave secreta de 16, 24 o 32 caracteres
	 * @param iv valor de arranque del cifrado, no vacío
	 * @return el Cipher listo para usar
	 * @throws GeneralSecurityException si el algoritmo o los parámetros no sonválidos
	 * @throws IllegalArgumentException si la llave o el IV no cumplen el tamaño
	 *                                  requerido
	 */
	private static Cipher crearCipher(int modo, String llave, String iv) throws GeneralSecurityException {
		if (llave == null) {
			throw new IllegalArgumentException("La llave de cifrado (aes.llave) no está configurada");
		}
		if (iv == null || iv.isEmpty()) {
			throw new IllegalArgumentException("El IV de cifrado (aes.iv) no está configurado");
		}

		byte[] bytesLlave = llave.getBytes(StandardCharsets.UTF_8);
		if (bytesLlave.length != 16 && bytesLlave.length != 24 && bytesLlave.length != 32) {
			throw new IllegalArgumentException(
					"La llave aes.llave debe medir 16, 24 o 32 caracteres (sin tildes) y mide " + bytesLlave.length);
		}

		SecretKeySpec llaveSecreta = new SecretKeySpec(bytesLlave, ALGORITMO);
		GCMParameterSpec parametros = new GCMParameterSpec(TAMANO_ETIQUETA_BITS, iv.getBytes(StandardCharsets.UTF_8));

		Cipher cipher = Cipher.getInstance(TIPO_CIFRADO);
		cipher.init(modo, llaveSecreta, parametros);
		return cipher;
	}

}
