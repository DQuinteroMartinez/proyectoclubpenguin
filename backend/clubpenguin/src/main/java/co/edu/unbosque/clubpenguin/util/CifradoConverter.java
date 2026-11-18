package co.edu.unbosque.clubpenguin.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convierte automáticamente un texto al guardarlo y al leerlo de la base de
 * datos: lo cifra con AES al guardar y lo descifra al leer. Se aplica solo a
 * los atributos marcados con @Convert.
 */
@Converter
@Component
public class CifradoConverter implements AttributeConverter<String, String> {

	/** Llave secreta de cifrado, leída de application.properties. */
	private final String llave;

	/** Valor de arranque del cifrado, leído de application.properties. */
	private final String iv;

	/**
	 * Crea el conversor. Spring entrega los valores de la configuración.
	 *
	 * @param llave llave secreta de cifrado (propiedad aes.llave)
	 * @param iv    valor de arranque del cifrado (propiedad aes.iv)
	 */
	public CifradoConverter(@Value("${aes.llave}") String llave, @Value("${aes.iv}") String iv) {
		this.llave = llave;
		this.iv = iv;
	}

	/**
	 * Cifra el valor antes de guardarlo en la base de datos.
	 *
	 * @param atributo texto original, tal como lo ve el código
	 * @return el texto cifrado, o null si el valor es null
	 */
	@Override
	public String convertToDatabaseColumn(String atributo) {
		if (atributo == null) {
			return null;
		}
		return AESUtil.encrypt(llave, iv, atributo);
	}

	/**
	 * Descifra el valor al leerlo de la base de datos.
	 *
	 * @param dato texto cifrado, tal como está guardado
	 * @return el texto original, o null si el valor es null
	 */
	@Override
	public String convertToEntityAttribute(String dato) {
		if (dato == null) {
			return null;
		}
		return AESUtil.decrypt(llave, iv, dato);
	}
}
