import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Autenticacionservice } from '../../services/autenticacionservice';
import { required } from '@angular/forms/signals';
import { Loginrequestmodel } from '../../models/loginrequestmodel';

/** Pantalla de inicio de sesion. También muestra el formulario, valida los campos y el login en
 * Autenticacionservice */

@Component({
  selector: 'app-logincomponent',
  standalone: false,
  styleUrl: './logincomponent.css',
  templateUrl: './logincomponent.html',
})

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
  mensajeExito = ' ';

  constructor (private fb: FormBuilder, private autenticacion: Autenticacionservice) {
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
    this.mensajeExito = '';

    this.autenticacion.login(this.formulario.getRawValue() as Loginrequestmodel).subscribe({
      next: (respuesta) => {
        this.cargando = false;
        this.mensajeExito = `Sesión iniciada como ${respuesta.nombreUsuario} (${respuesta.rol})`;
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
