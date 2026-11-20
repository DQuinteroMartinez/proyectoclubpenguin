import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Usuariodtomodel } from '../models/usuariodtomodel';

/**
 * Servicio que consulta los usuarios con cuenta en el backend.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})
export class Usuarioservice {

  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Obtiene la lista de todos los usuarios con cuenta.
   * Llama a GET /api/usuarios (requiere token, que agrega el interceptor).
   * @returns un Observable con el arreglo de usuarios.
   */
  listar(): Observable<Usuariodtomodel[]> {
    return this.http.get<Usuariodtomodel[]>(`${this.apiUrl}/usuarios`);
  }
}
