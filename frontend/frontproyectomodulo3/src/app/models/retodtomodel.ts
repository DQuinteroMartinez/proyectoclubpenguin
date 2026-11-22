export interface Retodtomodel {
  /** Reto como lo devuelve el back */

  id: number;
  /** Identificador del reto */

  retador: string;
  /** Nombre del usuario que envió el reto */

  retado: string;
  /** Nombre del usuario que recibió el reto */

  minijuego: string;
  /** Nombre del minijuego del reto */

  puntajeObjetivo: number;
  /** Puntaje que el retado debe superar */

  puntajeRetado: number | null;
  /** Puntaje con el que el retado resolvió el reto, sale null mientras esté pendiente */

  estado: 'PENDIENTE' | 'SUPERADO' | 'NO_SUPERADO';
  /** Literalmente el estado del reto */

  fechaCreacion: string;
  /** Fecha creación en texto ISO-8601 sin zona horaria */

  fechaResolucion: string | null;
  /** Fecha de resolución en texto ISO-8601, sale null mientras esté pendiente */
}
