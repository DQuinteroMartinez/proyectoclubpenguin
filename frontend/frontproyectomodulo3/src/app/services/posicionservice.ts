import { Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Posiciondtomodel } from '../models/posiciondtomodel';

/**
 * Servicio que guarda y recupera la última posición del jugador en el mapa.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})
@Service()
export class Posicionservice {
  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Obtiene la última posición guardada del jugador (0, 0 si nunca se ha movido).
   * Llama a GET /api/posicion (requiere token, que agrega el interceptor).
   * @returns un Observable con la posición (x, y).
   */
  obtener(): Observable<Posiciondtomodel> {
    return this.http.get<Posiciondtomodel>(`${this.apiUrl}/posicion`);
  }

  /**
   * Guarda la posición actual del jugador. Debe llamarse cuando el jugador se
   * detiene o cada pocos segundos, no en cada píxel de movimiento.
   * Llama a PUT /api/posicion (requiere token, que agrega el interceptor).
   * @param posicion coordenadas (x, y), enteros mayores o iguales a 0.
   * @returns un Observable con la posición guardada.
   */
  guardar(posicion: Posiciondtomodel): Observable<Posiciondtomodel> {
    return this.http.put<Posiciondtomodel>(`${this.apiUrl}/posicion`, posicion);
  }
}
