import { Component, HostListener } from '@angular/core';

/**  Este es uno de los minijuegos. El jugador mueve una canasta con las flechas del teclado
 * para atrapar bolsas que caen */

@Component({
  selector: 'app-atrapabolsascomponent',
  standalone: false,
  styleUrl: './atrapabolsascomponent.css',
  templateUrl: './atrapabolsascomponent.html',
})
export class Atrapabolsascomponent {

  readonly anchoEscenario = 600;
  readonly altoEscenario = 400;
  readonly anchoCanasta = 70;
  readonly altoCanasta = 40;
  readonly paso = 20;

  x = (this.anchoEscenario - this.anchoCanasta) /2;

  @HostListener('window:keydown', ['$event'])
  moverCanasta(evento: KeyboardEvent) {

    const maximoX = this.anchoEscenario - this.anchoCanasta;

    switch(evento.key){

      case 'ArrowLeft':
        this.x = Math.max(0, this.x - this.paso);
        break;

      case 'ArrowRight':
        this.x = Math.max(maximoX, this.x + this.paso);
        break;

      default:
        return;
    }
    evento.preventDefault();
  }
  /** Ayuda a mover la canasta de izquierda a derecha cuando se pulsa una flecha del teclado.
   * También se limita la posicion para que la canasta no salga del area limitada */
}
