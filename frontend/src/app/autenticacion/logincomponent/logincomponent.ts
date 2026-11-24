import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Loginrequestmodel } from '../../models/loginrequestmodel';
import { Autenticacionservice } from '../../services/autenticacionservice';
import { Router } from '@angular/router';

/** Pantalla de inicio de sesion. También muestra el formulario, valida los campos y el login en
 * Autenticacionservice */

@Component({
  selector: 'app-logincomponent',
  standalone: false,
  templateUrl: './logincomponent.html',
  styleUrl: './logincomponent.css',
})

export class Logincomponent {

  formulario: FormGroup;
  cargando = signal(false);
  mensajeError = signal('');

  constructor (private fb: FormBuilder, private autenticacion: Autenticacionservice, private router: Router) {
    this.formulario = this.fb.group({
      nombreUsuario: ['',Validators.required],
      contrasena: ['', Validators.required],
    });
  }


  iniciarSesion(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }
    this.cargando.set(true);
    this.mensajeError.set('');

    this.autenticacion.login(this.formulario.getRawValue() as Loginrequestmodel).subscribe({
      next: () => {
        this.cargando.set(false);
        this.router.navigate(['/mapa']);
      },
      error: (error) => {
        this.cargando.set(false);
        this.mensajeError.set(error.status === 0
        ? 'No se pudo conectar con el servidor' : (error.error?.error ?? 'Ocurrió un error inesperado'));
      },
    });

  }
  /** Envía credenciales al backend si el formulario es válido y muestra un mensaje que éxtio o el
   * error recibido */
}
