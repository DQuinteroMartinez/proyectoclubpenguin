import { Component, HostListener } from '@angular/core';
import { Autenticacionservice } from '../../services/autenticacionservice';

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
  readonly paso = 10;

  x =(this.anchoEscenario - this.tamanoPinguino) /2;
  y = (this.altoEscenario - this.tamanoPinguino) /2;
  esAdmin = false;

  constructor(private autenticacion: Autenticacionservice) {
    this.esAdmin = this.autenticacion.esAdmin();
  }

  @HostListener('window:keydown', ['$event'])
  moverPinguino(evento: KeyboardEvent) {

    const maximoX = this.anchoEscenario - this.tamanoPinguino;
    const maximoY = this.anchoEscenario - this.tamanoPinguino;

    switch (evento.key) {

      case 'ArrowLeft':
        this.x = Math.max(0, this.x -this.paso);
        break;

      case 'ArrowRight':
        this.x = Math.max(maximoX, this.x + this.paso);
        break;

      case 'ArrowUp':
        this.y = Math.max(0, this.y - this.paso);
        break;

      case 'ArrowDown':
        this.y = Math.max(maximoY, this.y - this.paso);
        break;

      default:
        return;
    }
    evento.preventDefault();
  }
  /** Este método mueve la posición del pingüino cuando se pulsa una flecha del teclado.
   * Tener en cuenta que la posición se limita para que el pingüino no se salga del marco */
}
