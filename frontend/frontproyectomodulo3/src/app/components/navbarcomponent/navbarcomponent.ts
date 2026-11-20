import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Autenticacionservice } from '../../services/autenticacionservice';
import { Router } from '@angular/router';

/** Aqui se encuentra la barra superior (menu) de la aplicacion. */

@Component({
  selector: 'app-navbarcomponent',
  standalone: false,
  styleUrl: './navbarcomponent.css',
  templateUrl: './navbarcomponent.html',
})
export class Navbarcomponent {

  sesionIniciada$: Observable<boolean>;
  esAdmin$: Observable<boolean>;

  constructor (private autenticacion: Autenticacionservice, private router: Router) {
    this.sesionIniciada$ = this.autenticacion.sesionIniciada$;
    this.esAdmin$ = this.autenticacion.esAdmin$;
  }

  get nombreUsuario(): string | null {
    return this.autenticacion.obtenerNombreUsuario();
  }

  cerrarSesion(): void {
    this.autenticacion.cerrarSesion();
    this.router.navigate(['/login']);
  }
}
