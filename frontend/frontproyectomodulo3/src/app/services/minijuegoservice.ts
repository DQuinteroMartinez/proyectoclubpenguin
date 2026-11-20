import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Minijuegodtomodel } from '../models/minijuegodtomodel';

/**
 * Servicio que consulta el catálogo de minijuegos en el backend.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})
export class Minijuegoservice {
  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Obtiene el catálogo de los 5 minijuegos disponibles.
   * Llama a GET /api/minijuegos (requiere token, que agrega el interceptor).
   * @returns un Observable con el arreglo de minijuegos.
   */
  listar(): Observable<Minijuegodtomodel[]> {
    return this.http.get<Minijuegodtomodel[]>(`${this.apiUrl}/minijuegos`);
  }
}
