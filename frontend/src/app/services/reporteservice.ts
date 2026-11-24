import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

/**
 * Servicio que descarga los reportes en PDF generados por el backend.
 * Los componentes no hacen peticiones HTTP: siempre pasan por este servicio.
 */
@Injectable({
  providedIn: 'root',
})

export class Reporteservice {
  /** URL base de la API, tomada del archivo de environments. */
  private readonly apiUrl = environment.apiUrl;

  /**
   * Recibe HttpClient por inyección de dependencias.
   * @param http cliente HTTP de Angular que envía las peticiones al backend.
   */
  constructor(private http: HttpClient) {}

  /**
   * Pide el PDF del ranking de un minijuego y lo descarga en el navegador.
   * Llama a GET /api/reportes/ranking/{minijuegoId} (requiere token, que agrega el interceptor).
   * @param minijuegoId identificador del minijuego (del 1 al 5).
   * @returns un Observable que se completa cuando la descarga fue disparada.
   */
  descargarRanking(minijuegoId: number): Observable<void> {
    return this.http
      .get(`${this.apiUrl}/reportes/ranking/${minijuegoId}`, {
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        map((respuesta: HttpResponse<Blob>) => {
          const nombre =
            this.extraerNombre(respuesta.headers.get('Content-Disposition')) ??
            `ranking-${minijuegoId}.pdf`;
          this.guardarArchivo(respuesta.body as Blob, nombre);
        })
      );
  }
  /**
   * Lee el nombre del archivo desde el encabezado Content-Disposition.
   * @param encabezado valor del encabezado, o null si no vino.
   * @returns el nombre del archivo, o null si no se pudo leer.
   */
  private extraerNombre(encabezado: string | null): string | null {
    if (!encabezado) {
      return null;
    }
    const coincidencia = /filename="?([^";]+)"?/.exec(encabezado);
    return coincidencia ? coincidencia[1] : null;
  }

  /**
   * Dispara la descarga de un archivo desde el navegador.
   * Crea una dirección temporal para el blob y la abre con un enlace invisible.
   * @param archivo contenido del archivo recibido del backend.
   * @param nombre nombre con el que se guardará el archivo.
   */
  private guardarArchivo(archivo: Blob, nombre: string): void {
    const url = URL.createObjectURL(archivo);
    const enlace = document.createElement('a');
    enlace.href = url;
    enlace.download = nombre;
    enlace.click();
    URL.revokeObjectURL(url);
  }
}

