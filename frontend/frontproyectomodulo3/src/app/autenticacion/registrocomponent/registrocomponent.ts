import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Autenticacionservice } from '../../services/autenticacionservice';
import { Registrorequestmodel } from '../../models/registrorequestmodel';
import { HttpErrorResponse } from '@angular/common/http';


/** Pantall de registro para cuentas nuevas. En esta valida los campos igual que el backend y
 * encomienda el registro en autenticacionservice */

@Component({
  selector: 'app-registrocomponent',
  standalone: false,
  styleUrl: './registrocomponent.css',
  templateUrl: './registrocomponent.html',
})
export class Registrocomponent {

  formulario: FormGroup;
  cargando = false;
  mensajeError = '';
  mensajeExito = '';
  erroresServidor: Record<string, string> = {};

  constructor (private fb: FormBuilder, private autenticacion: Autenticacionservice) {
    this.formulario = this.fb.group({
      nombreUsuario: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      correo: ['',[Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  registrarse(): void{
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando = true;
    this.mensajeError = '';
    this.mensajeExito = '';
    this.erroresServidor = {};

    this.autenticacion.registro(this.formulario.getRawValue() as Registrorequestmodel).subscribe({
      next: (usuario) => {
        this.cargando = false;
        this.mensajeExito = `Cuenta creada para ${usuario.nombreUsuario}. Ya puedes iniciar sesión.`;
        this.formulario.reset();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando = false;
        this.manejaError(error);
      },
    });
  }
  /** Envpia los datos al backend si el formulario es válido. Si sale bien, sale un mensaje de éxito,
   * si no, muestra los errores */

  private manejaError(error: HttpErrorResponse): void {
    if (error.status === 0) {
      this.mensajeError = 'No se pudo conectar con el servidor';
      return;
    }

    const cuerpo = error.error;
    if (error.status === 400 && cuerpo && !cuerpo.error){
      this.erroresServidor = cuerpo;
      return;
    }
    this.mensajeError = cuerpo?.error ?? 'Ocurrió un error inesperado';
  }
  /** Interpreta el error en el backend y lo deja listo para mostrarse. SI un cuerpo con la clave
   * "error" aparece, es un error de negocio */

}
