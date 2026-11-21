import { Component } from '@angular/core';

/** Aqui se encuentra la pantalla del mapa de exploración. Muestra el escenario y el pingüino del
 * jugador en una posición */

@Component({
  selector: 'app-mapacomponent',
  standalone: false,
  styleUrl: './mapacomponent.css',
  templateUrl: './mapacomponent.html',
})
export class Mapacomponent {

  readonly anchoEscenario = 800;
  readonly altoEscenario = 500;
  readonly tamanoPinguino = 40;

  x =(this.anchoEscenario - this.tamanoPinguino) /2;
  y = (this.altoEscenario - this.tamanoPinguino) /2;
}
