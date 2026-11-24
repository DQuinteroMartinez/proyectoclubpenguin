import { Component, OnInit, signal } from '@angular/core';
import { Partidadtomodel} from '../../models/partidadtomodel';
import { Partidaservice } from '../../services/partidaservice';
import { HttpErrorResponse } from '@angular/common/http';

/** Pantalla del historial de partidas del jugador. Le pide al backend las partidas y las muestra
 * en una tabla, de mas reciente a mas antigua */

@Component({
  selector: 'app-historialcomponent',
  standalone: false,
  styleUrl: './historialcomponent.css',
  templateUrl: './historialcomponent.html',
})
export class Historialcomponent implements OnInit {

  partidas = signal<Partidadtomodel[]> ([]);
  cargando = signal(true);
  mensajeError = signal('');

  constructor(private partidaservice: Partidaservice) {
  }

  ngOnInit() {
    this.partidaservice.misPartidas().subscribe({ next: (lista) => {
      this.partidas.set(lista);
      this.cargando.set(false);
    },
      error: (error: HttpErrorResponse) => {
      this.cargando.set(false);
      this.mensajeError.set( error.status === 0 ? 'No se pudo conectar con el servidor' :
        (error.error?.error ?? 'No se pudo cargar el historial de partidas'));
      }
    });
  }
  /** Se ejecuta al mostrarse el componente, cargando el historial de las partidas */

}
