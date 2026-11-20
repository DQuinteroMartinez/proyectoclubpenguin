import { Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Partidadtomodel } from '../models/partidadtomodel';
import { Partidarequestmodel } from '../models/partidarequestmodel';

/**
 * Servicio que registra las partidas jugadas y consulta el historial del usuario.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})()
export class Partidaservice {
  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Registra una partida terminada. El usuario se identifica por el token.
   * Llama a POST /api/partidas (requiere token, que agrega el interceptor).
   * El backend también resuelve los retos pendientes del jugador en ese minijuego.
   * @param datos identificador del minijuego y puntaje obtenido (entero >= 0).
   * @returns un Observable con la partida ya guardada.
   */
  registrar(datos: Partidarequestmodel): Observable<Partidadtomodel> {
    return this.http.post<Partidadtomodel>(`${this.apiUrl}/partidas`, datos);
  }

  /**
   * Obtiene el historial de partidas del usuario autenticado.
   * Llama a GET /api/partidas/mias (requiere token, que agrega el interceptor).
   * @returns un Observable con las partidas, de la más reciente a la más antigua.
   */
  misPartidas(): Observable<Partidadtomodel[]> {
    return this.http.get<Partidadtomodel[]>(`${this.apiUrl}/partidas/mias`);
  }
}
