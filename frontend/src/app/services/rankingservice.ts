import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Rankingdtomodel } from '../models/rankingdtomodel';

/**
 * Servicio que consulta el ranking (Top 10) de cada minijuego en el backend.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})
export class Rankingservice {
  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Obtiene el Top 10 de un minijuego (mejor puntaje por usuario).
   * Llama a GET /api/rankings/{minijuegoId} (requiere token, que agrega el interceptor).
   * Responde 404 si el minijuego no existe y una lista vacía si nadie ha jugado.
   * @param minijuegoId identificador del minijuego (del 1 al 5).
   * @returns un Observable con las filas del ranking.
   */
  porMinijuego(minijuegoId: number): Observable<Rankingdtomodel[]> {
    return this.http.get<Rankingdtomodel[]>(`${this.apiUrl}/rankings/${minijuegoId}`);
  }
}
