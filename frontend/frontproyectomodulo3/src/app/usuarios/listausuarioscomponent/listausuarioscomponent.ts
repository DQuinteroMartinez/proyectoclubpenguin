import { Component } from '@angular/core';
import { Usuariodtomodel } from '../../models/usuariodtomodel';
import { Usuarioservice } from '../../services/usuarioservice';

/** Pantalla que muestra la lista de usuarios que cuentan con una cuenta. Le pide los datos a
 * usuarioservic al abrirse y los muestra en una tabla*/

@Component({
  selector: 'app-listausuarioscomponent',
  standalone: false,
  styleUrl: './listausuarioscomponent.css',
  templateUrl: './listausuarioscomponent.html',
})
export class Listausuarioscomponent {

  usuarios: Usuariodtomodel[] = [];
  cargando = true;
  mensajeError = '';

  constructor (private usuarioservice: Usuarioservice){}

  ngOnInit(): void {
    this.usuarioservice.listar().subscribe({
      next: (lista) => {
        this.usuarios = lista;
        this.cargando = false;
      },
      error: (error) => {
        this.cargando = false;
        this.mensajeError = error.status === 0 ?
          'No se pudo conectar con el servidor' : (error.error?.error ?? 'No se pudo cargar la lista de usuarios');
      },
    });
  }
  /** Se ejecuta al mostrarse el componente: se carga la lista de usuarios
   * */
}
