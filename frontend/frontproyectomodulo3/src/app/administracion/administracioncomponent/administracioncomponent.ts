import { Component, OnInit, signal } from '@angular/core';
import { Usuariodtomodel } from '../../models/usuariodtomodel';
import { Usuarioservice } from '../../services/usuarioservice';
import { Administracionservice } from '../../services/administracionservice';
import { Autenticacionservice } from '../../services/autenticacionservice';
import { HttpErrorResponse } from '@angular/common/http';

/** Panel de administracion, donde muestra todos los usuarios y permite cambiar su rol o eliminar
 * sus cuentas*/

@Component({
  selector: 'app-administracioncomponent',
  standalone: false,
  styleUrl: './administracioncomponent.css',
  templateUrl: './administracioncomponent.html',
})
export class Administracioncomponent implements OnInit {

  usuarios = signal<Usuariodtomodel[]>([]);
  cargando = signal(true);
  mensajeError = signal('');
  procesandoId = signal<number | null>(null);
  mensajeAccionError = signal('');
  mensajeAccionExito = signal('');
  miNombre: string | null;

  constructor( private usuarioservice: Usuarioservice, private administracionservice: Administracionservice,
               private autenticacion: Autenticacionservice) {
    this.miNombre = this.autenticacion.obtenerNombreUsuario();
  }

  ngOnInit() {
    this.cargarUsuarios();
  }
  /** Se ejecuta al mostrarse el component, cuando carga la lista de usuarios */

  cargarUsuarios(): void {

    this.cargando.set(true);
    this.mensajeError.set('');
    this.usuarioservice.listar().subscribe({
      next: (lista) => {
        this.usuarios.set(lista);
        this.cargando.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.cargando.set(false);
        this.mensajeError.set(this.mensajeDe(error, 'No se puede cargar la lista de usuarios'));
      },
    });
  }
  /** Pide la lista de usuarios al backend */

  cambiarRol(usuario: Usuariodtomodel): void {

    const nuevoRol: 'ADMIN' | 'USUARIO' = usuario.rol === 'ADMIN' ? 'USUARIO': 'ADMIN';
    this.procesandoId.set(usuario.id);
    this.mensajeAccionExito.set('');
    this.mensajeAccionExito.set('');
    this.administracionservice.cambiarRol(usuario.id, {rol: nuevoRol}).subscribe({
      next:(actualizado) => {
        this.procesandoId.set(null);
        this.usuarios.update((lista) => lista.map((u) => (u.id === actualizado.id ? actualizado: u))
        );
        this.mensajeAccionExito.set(`${actualizado.nombreUsuario} ahora tiene el rol
        ${actualizado.rol},`);
      },
      error: (error: HttpErrorResponse) => {
        this.procesandoId.set(null);
        this.mensajeAccionError.set(this.mensajeDe(error, 'No se pudo cambiar el rol'));
      },
    });
  }
  /** Alterna el rol de un usuario, cambiando de Usuario a administrador o viceversa */

  eliminar (usuario: Usuariodtomodel): void {

    const confirmado = window.confirm(`¿Eliminar la cuenta de ${usuario.nombreUsuario}?
    Tambien se borraran sus partidas y sus retos. Esta accion no se puede deshacer.`);

    if (!confirmado) {
      return;
    }

    this.procesandoId.set(usuario.id);
    this.mensajeAccionError.set('');
    this.mensajeAccionExito.set('');
    this.administracionservice.eliminar(usuario.id).subscribe({
      next: () => {
        this.procesandoId.set(null);
        this.usuarios.update((lista) => lista.filter((u) => u.id !== usuario.id));
        this.mensajeAccionExito.set(`La cuenta de ${usuario.nombreUsuario} fue eliminada.`);
      },
      error: (error: HttpErrorResponse) => {
        this.procesandoId.set(null);
        this.mensajeAccionError.set(this.mensajeDe(error, 'No se pudo eliminar la cuenta'));
      }
    });
  }
  /** Elimina la cuenta de un usuario después de pedir confirmacion */

  esMiCuenta (usuario: Usuariodtomodel): boolean {
    return usuario.nombreUsuario === this.miNombre;
  }
  /** Indica si una fila corresponde al usuario con sesión */

  private mensajeDe (error: HttpErrorResponse, mensajePorDefecto: string): string {

    if (error.status === 0){
      return 'No se pudo conectar con el servidor';
    }
    if (error.status === 403){
      return 'No tienes permiso para realizar esta acción';
    }
    return error.error?.error ?? mensajePorDefecto;
  }
  /** Convierte el error de una peticion es un mensaje para el usuario. Lo que es erro 403 y
   * sin conexion tienen mensajes propios, el error se muestra tal cual */

}


