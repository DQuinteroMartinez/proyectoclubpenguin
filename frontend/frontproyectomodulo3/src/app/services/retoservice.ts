import { HttpClient } from '@angular/common/http';
import { Service } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Retodtomodel } from '../models/retodtomodel';
import { Retorequestmodel } from '../models/retorequestmodel';

/**
 * Servicio que crea retos entre usuarios y consulta el historial de retos.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})
export class Retoservice {
  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Reta a otro usuario en un minijuego. El backend calcula el puntaje a superar
   * con el mejor puntaje del retador y envía un correo al retado.
   * Llama a POST /api/retos (requiere token, que agrega el interceptor).
   * Errores posibles: 400 (retarse a sí mismo o no haber jugado ese minijuego),
   * 404 (usuario o minijuego inexistente) y 502 (falló el correo; el reto no se guarda).
   * @param datos nombre del usuario retado e identificador del minijuego.
   * @returns un Observable con el reto creado.
   */
  crear(datos: Retorequestmodel): Observable<Retodtomodel> {
    return this.http.post<Retodtomodel>(`${this.apiUrl}/retos`, datos);
  }

  /**
   * Obtiene los retos que el usuario lanzó y recibió.
   * Llama a GET /api/retos/mios (requiere token, que agrega el interceptor).
   * @returns un Observable con los retos, del más reciente al más antiguo.
   */
  misRetos(): Observable<Retodtomodel[]> {
    return this.http.get<Retodtomodel[]>(`${this.apiUrl}/retos/mios`);
  }
}
