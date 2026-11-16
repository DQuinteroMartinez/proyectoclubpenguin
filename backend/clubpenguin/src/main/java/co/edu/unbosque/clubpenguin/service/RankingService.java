package co.edu.unbosque.clubpenguin.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.edu.unbosque.clubpenguin.dto.RankingDto;
import co.edu.unbosque.clubpenguin.exception.RecursoNoEncontradoException;
import co.edu.unbosque.clubpenguin.model.Partida;
import co.edu.unbosque.clubpenguin.repository.MinijuegoRepository;
import co.edu.unbosque.clubpenguin.repository.PartidaRepository;

/**
 * Contiene la lógica de negocio de los rankings: arma la tabla de posiciones de
 * cada minijuego.
 */
@Service

public class RankingService {

	/** Cantidad máxima de usuarios que aparecen en un ranking. */
	private static final int LIMITE_RANKING = 10;

	/** Repositorio para consultar las partidas. */
	private final PartidaRepository partidaRepository;

	/** Repositorio para comprobar que el minijuego exista. */
	private final MinijuegoRepository minijuegoRepository;

	/**
	 * Crea el service. Spring entrega automáticamente cada dependencia.
	 *
	 * @param partidaRepository   repositorio de partidas
	 * @param minijuegoRepository repositorio de minijuegos
	 */
	public RankingService(PartidaRepository partidaRepository, MinijuegoRepository minijuegoRepository) {
		this.partidaRepository = partidaRepository;
		this.minijuegoRepository = minijuegoRepository;
	}

	/**
	 * Obtiene el ranking de un minijuego con el mejor puntaje de cada usuario. Los
	 * usuarios se ordenan de mayor a menor puntaje y, si empatan, alfabéticamente.
	 *
	 * @param minijuegoId identificador del minijuego
	 * @return la lista de filas del ranking, numeradas desde la posición 1
	 * @throws RecursoNoEncontradoException si el minijuego no existe
	 */
	public List<RankingDto> obtenerRanking(Long minijuegoId) {
		if (!minijuegoRepository.existsById(minijuegoId)) {
			throw new RecursoNoEncontradoException("Minijuego no encontrado");
		}

		List<Partida> partidas = partidaRepository.findByMinijuegoId(minijuegoId);

		// Paso 1: guardar el mejor puntaje de cada usuario (clave: nombre, valor:
		// puntaje)
		Map<String, Integer> mejoresPuntajes = new HashMap<>();
		for (Partida partida : partidas) {
			String nombre = partida.getUsuario().getNombreUsuario();
			Integer puntajeGuardado = mejoresPuntajes.get(nombre);

			if (puntajeGuardado == null || partida.getPuntaje() > puntajeGuardado) {
				mejoresPuntajes.put(nombre, partida.getPuntaje());
			}
		}

		// Paso 2: convertir el mapa en una lista de filas del ranking
		List<RankingDto> filas = new ArrayList<>();
		for (Map.Entry<String, Integer> entrada : mejoresPuntajes.entrySet()) {
			filas.add(new RankingDto(entrada.getKey(), entrada.getValue()));
		}

		// Paso 3: ordenar de mayor a menor puntaje (si empatan, por nombre)
		filas.sort(Comparator.comparing(RankingDto::getMejorPuntaje, Comparator.reverseOrder())
				.thenComparing(RankingDto::getNombreUsuario));

		// Paso 4: quedarse con los primeros 10 y asignarles la posición
		List<RankingDto> ranking = new ArrayList<>();
		for (int i = 0; i < filas.size() && i < LIMITE_RANKING; i++) {
			RankingDto fila = filas.get(i);
			fila.setPosicion(i + 1);
			ranking.add(fila);
		}

		return ranking;
	}

}
