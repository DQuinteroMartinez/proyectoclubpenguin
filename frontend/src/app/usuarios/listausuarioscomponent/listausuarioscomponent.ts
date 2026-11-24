import { Component, signal } from '@angular/core';
import { Usuariodtomodel } from '../../models/usuariodtomodel';
import { Usuarioservice } from '../../services/usuarioservice';
import { Autenticacionservice } from '../../services/autenticacionservice';

/** Pantalla que muestra la lista de usuarios que cuentan con una cuenta. Le pide los datos a
 * usuarioservic al abrirse y los muestra en una tabla*/

@Component({
  selector: 'app-listausuarioscomponent',
  standalone: false,
  styleUrl: './listausuarioscomponent.css',
  templateUrl: './listausuarioscomponent.html',
})
export class Listausuarioscomponent {

  usuarios = signal<Usuariodtomodel[]>([]);
  cargando = signal(true);
  mensajeError = signal('');
  esAdmin = signal(false);

  constructor (private usuarioservice: Usuarioservice, private autenticacion: Autenticacionservice){
    this.esAdmin.set(this.autenticacion.esAdmin());
  }
  /** Se guarda si el usuario es administrador: solo el admin puede ver los correos de los demás */

  ngOnInit(): void {
    this.usuarioservice.listar().subscribe({
      next: (lista) => {
        this.usuarios.set(lista);
        this.cargando.set(false);
      },
      error: (error) => {
        this.cargando.set(false);
        this.mensajeError.set(error.status === 0 ?
          'No se pudo conectar con el servidor' : (error.error?.error ?? 'No se pudo cargar la lista de usuarios'));
      },
    });
  }
  /** Se ejecuta al mostrarse el componente: se carga la lista de usuarios
   * */
}
