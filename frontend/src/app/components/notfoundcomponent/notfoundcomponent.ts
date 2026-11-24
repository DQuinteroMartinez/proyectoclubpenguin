import { Component } from '@angular/core';

/** Pantalla que se muestra cuando el jugador escribe una dirección que no existe en la aplicación.
 * Explica el problema y ofrece un enlace para volver al mapa (si no hay sesión, el guard
 * del mapa lo lleva al login). */
@Component({
  selector: 'app-notfoundcomponent',
  standalone: false,
  styleUrl: './notfoundcomponent.css',
  templateUrl: './notfoundcomponent.html',
})
export class Notfoundcomponent {}
