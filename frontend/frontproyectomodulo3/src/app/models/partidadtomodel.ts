export interface Partidadtomodel {
  /** Partida ya registrada en el backend */

  id: number;
  /** Identificador de la partida */

  nombreUsuario: string;
  /** Nombre del usuario que jugó */

  minijuego: string;
  /** Nombre del minijuego jugado */

  puntaje: number;
  /** Puntaje que se obtuvo */

  fecha: string;
  /** Fecha de la partida en texto ISO-8601 sin zona horaria */
}
