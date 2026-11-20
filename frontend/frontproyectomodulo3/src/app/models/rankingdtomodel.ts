export interface Rankingdtomodel {
  /** Fila del ranking del minijuego, mostrando el top 10 */

  posicion: number;
  /** Posicion en el ranking, siendo 1 el mejor */

  nombreUsuario: string;
  /** Nombre del jugador */

  mejorPuntaje: number;
  /** Mejor puntaje del jugador en el minijuego */
}
