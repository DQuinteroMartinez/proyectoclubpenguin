import { Component, signal } from '@angular/core';
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
  cargando = signal(false);
  mensajeError = signal('');
  mensajeExito = signal('');
  erroresServidor = signal<Record<string, string>>({});

  constructor (private fb: FormBuilder, private autenticacion: Autenticacionservice) {
    this.formulario = this.fb.group({
      nombreUsuario: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
      correo: ['', [Validators.required, Validators.email, Validators.pattern(/^[^\s@]+@[^\s@]+\.[a-zA-Z]{2,}$/)]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  registrarse(): void{
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando.set(true);
    this.mensajeError.set('');
    this.mensajeExito.set('');
    this.erroresServidor.set({});

    this.autenticacion.registro(this.formulario.getRawValue() as Registrorequestmodel).subscribe({
      next: (usuario) => {
        this.cargando.set(false);
        this.mensajeExito.set(`Cuenta creada para ${usuario.nombreUsuario}. Ya puedes iniciar sesión.`);
        this.formulario.reset();
      },
      error: (error: HttpErrorResponse) => {
        this.cargando.set(false);
        this.manejaError(error);
      },
    });
  }
  /** Envpia los datos al backend si el formulario es válido. Si sale bien, sale un mensaje de éxito,
   * si no, muestra los errores */

  private manejaError(error: HttpErrorResponse): void {
    if (error.status === 0) {
      this.mensajeError.set('No se pudo conectar con el servidor');
      return;
    }

    const cuerpo = error.error;
    if (error.status === 400 && cuerpo && !cuerpo.error){
      this.erroresServidor.set(cuerpo);
      return;
    }
    this.mensajeError.set(cuerpo?.error ?? 'Ocurrió un error inesperado');
  }
  /** Interpreta el error en el backend y lo deja listo para mostrarse. SI un cuerpo con la clave
   * "error" aparece, es un error de negocio */

}
