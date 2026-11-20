import { Component } from '@angular/core';
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
  cargando = false;
  mensajeError = ' ';

  constructor (private fb: FormBuilder, private autenticacion: Autenticacionservice, private router: Router) {
    this.formulario = this.fb.group({
      nombreUsuario: ['',Validators.required],
      password: ['', Validators.required],
    });
  }


  iniciarSesion(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }
    this.cargando = true;
    this.mensajeError = '';

    this.autenticacion.login(this.formulario.getRawValue() as Loginrequestmodel).subscribe({
      next: () => {
        this.cargando = false;
        this.router.navigate(['/usuarios']);
      },
      error: (error) => {
        this.cargando = false;
        this.mensajeError = error.status === 0
        ? 'No se pudo conectar con el servidor' : (error.error?.errpr ?? 'Ocurrió un error inesperado');
      },
    });

  }
  /** Envía credenciales al backend si el formulario es válido y muestra un mensaje que éxtio o el
   * error recibido */
}
