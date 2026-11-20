import { Service } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Cambiorolrequestmodel } from '../models/cambiorolrequestmodel';
import { Usuariodtomodel } from '../models/usuariodtomodel';

/**
 * Servicio con las acciones exclusivas del administrador: cambiar el rol de un
 * usuario y eliminar cuentas. El backend responde 403 si el usuario no es ADMIN.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})
export class Administracionservice {

  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Cambia el rol de un usuario.
   * Llama a PUT /api/admin/usuarios/{id}/rol (solo ADMIN; el token lo agrega el interceptor).
   * @param id identificador del usuario.
   * @param datos nuevo rol: "ADMIN" o "USUARIO".
   * @returns un Observable con el usuario actualizado.
   */
  cambiarRol(id: number, datos: Cambiorolrequestmodel): Observable<Usuariodtomodel> {
    return this.http.put<Usuariodtomodel>(`${this.apiUrl}/admin/usuarios/${id}/rol`, datos);
  }

  /**
   * Elimina una cuenta junto con sus partidas y retos.
   * Llama a DELETE /api/admin/usuarios/{id} (solo ADMIN; responde 204 sin cuerpo).
   * @param id identificador del usuario a eliminar.
   * @returns un Observable que se completa cuando el usuario fue eliminado.
   */
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/admin/usuarios/${id}`);
  }
}
