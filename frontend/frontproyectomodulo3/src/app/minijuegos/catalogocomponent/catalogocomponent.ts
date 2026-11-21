import { Component, OnInit, signal } from '@angular/core';
import { Minijuegoservice } from '../../services/minijuegoservice';
import { HttpErrorResponse } from '@angular/common/http';
import { Minijuegodtomodel } from '../../models/minijuegodtomodel';

/** Muestra la pantalla del catálogo de minijuegos. Pide la lista de juegos al backend y muestra
 * una tarjeta por cada uno*/

@Component({
  selector: 'app-catalogocomponent',
  standalone: false,
  styleUrl: './catalogocomponent.css',
  templateUrl: './catalogocomponent.html',
})
export class Catalogocomponent implements OnInit {

  readonly rutas: Record<number, string> = {2: '/minijuegos/atrapabolsas'}

  minijuegos =signal<Minijuegodtomodel[]>([]);
  cargando = signal(true);
  mensajeError= signal('');

  constructor (private minijuegoservice: Minijuegoservice){}

  ngOnInit() {
    this.minijuegoservice.listar().subscribe({ next: (lista) => {
        this.minijuegos.set(lista);
        this.cargando.set(false);
        },
      error: (error: HttpErrorResponse) => {
      this.cargando.set(false);
      this.mensajeError.set( error.status === 0 ? 'No se pudo conectar con el servidor':
          (error.error?.error ?? 'No se pudo cargar el catálogo de los minijuegos'));
      },
    });
  }
/** Se ejecuta al mostrarse el componente, cagando el catalogo de minijuegos*/

  rutaDe (id: number): string | null {
    return this.rutas[id] ?? null;
  }
  /** Este devuelve la dirección del minijuego si ya existe en el front */
}
