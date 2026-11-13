package co.edu.unbosque.clubpenguin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.dto.MinijuegoDto;
import co.edu.unbosque.clubpenguin.model.Minijuego;
import co.edu.unbosque.clubpenguin.repository.MinijuegoRepository;

/**
 * Contiene la lógica de negocio relacionada con los minijuegos.
 */
@Service
public class MinijuegoService {

	/** Repositorio para consultar los minijuegos. */
	private final MinijuegoRepository minijuegoRepository;

	/**
	 * Crea el service. Spring entrega automáticamente el repositorio.
	 *
	 * @param minijuegoRepository repositorio de minijuegos
	 */
	public MinijuegoService(MinijuegoRepository minijuegoRepository) {
		this.minijuegoRepository = minijuegoRepository;
	}

	/**
	 * Obtiene el listado de todos los minijuegos disponibles.
	 *
	 * @return una lista con los datos de cada minijuego
	 */
	public List<MinijuegoDto> listar() {
		return minijuegoRepository.findAll().stream().map(this::convertirADto).collect(Collectors.toList());
	}

	/**
	 * Convierte un minijuego de la base de datos en un DTO para mostrar.
	 *
	 * @param minijuego minijuego que se quiere convertir
	 * @return el DTO con los datos del minijuego
	 */
	private MinijuegoDto convertirADto(Minijuego minijuego) {
		MinijuegoDto dto = new MinijuegoDto();
		dto.setId(minijuego.getId());
		dto.setNombre(minijuego.getNombre());
		dto.setDescripcion(minijuego.getDescripcion());
		return dto;
	}
}