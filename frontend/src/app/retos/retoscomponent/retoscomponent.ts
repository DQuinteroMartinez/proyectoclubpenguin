import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Usuariodtomodel } from '../../models/usuariodtomodel';
import { Minijuegodtomodel } from '../../models/minijuegodtomodel';
import { Retodtomodel } from '../../models/retodtomodel';
import { Autenticacionservice } from '../../services/autenticacionservice';
import { Usuarioservice } from '../../services/usuarioservice';
import { Minijuegoservice } from '../../services/minijuegoservice';
import { Retoservice } from '../../services/retoservice';
import { HttpErrorResponse } from '@angular/common/http';

/** Pantalla de retos, donde se permite retar a otro jugador en cualquier minijuego y consultar
 * el historial de retos, tanto los enviados como recibidos y su respectivo estado */
@Component({
  selector: 'app-retoscomponent',
  standalone: false,
  styleUrl: './retoscomponent.css',
  templateUrl: './retoscomponent.html',
})
export class Retoscomponent implements OnInit {

  readonly etiquetasEstado: Record<string, string> = { PENDIENTE: 'Pendiente', SUPERADO: 'Superado',
  NO_SUPERADO: 'No superado'};

  formulario: FormGroup;
  miNombre: string | null;

  rivales = signal<Usuariodtomodel[]>([]);
  minijuegos = signal<Minijuegodtomodel[]>([]);
  retos = signal<Retodtomodel[]>([]);
  cargandoRetos = signal(true);
  enviando = signal(false);
  mensajeErrorOpciones = signal('');
  mensajeErrorRetos = signal('');
  mensajeErrorEnvio = signal('');
  mensajeExito = signal('');

  constructor(private fb: FormBuilder, private autenticacion: Autenticacionservice, private usuarioservice: Usuarioservice,
              private minijuegoservice: Minijuegoservice, private retoservice: Retoservice) {
    this.miNombre = this.autenticacion.obtenerNombreUsuario();
    this.formulario = this.fb.group({
      nombreUsuarioRetado: ['', Validators.required], minijuegoId: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.cargarOpciones();
    this.cargarRetos();
  }

  private cargarOpciones(): void {

    this.usuarioservice.listar().subscribe({
      next: (lista) => this.rivales.set(lista.filter((usuario) => usuario.nombreUsuario
      !== this.miNombre)), error: (error: HttpErrorResponse) =>
        this.mensajeErrorOpciones.set(this.mensajeDe (error, 'No se pudo cargar la lista de jugadores'))
    });

    this.minijuegoservice.listar().subscribe({
      next: (lista) => this.minijuegos.set(lista), error: (error:HttpErrorResponse) =>
        this.mensajeErrorOpciones.set(this.mensajeDe (error, 'No se pudo cargar la lista de minijuegos')),
    });
  }

  cargarRetos(): void {
    this.cargandoRetos.set(true);
    this.mensajeErrorRetos.set('');
    this.retoservice.misRetos().subscribe({
      next: (lista) => {
        this.retos.set(lista);
        this.cargandoRetos.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoRetos.set(false);
        this.mensajeErrorRetos.set(this.mensajeDe(error, 'No se pudo cargar el historial de retos'));
      },
    });
  }

  enviarReto(): void {
    if (this.formulario.invalid){
      this.formulario.markAllAsTouched();
      return;
    }

    this.enviando.set(true);
    this.mensajeErrorEnvio.set('');
    this.mensajeExito.set('');

    const { nombreUsuarioRetado , minijuegoId } = this.formulario.getRawValue();
    this.retoservice.crear({ nombreUsuarioRetado, minijuegoId: Number(minijuegoId) })
      .subscribe({
        next: (reto) => {
          this.enviando.set(false);
          this.mensajeExito.set(`Reto enviado a ${reto.retado}. Debe superar ${reto.puntajeObjetivo} puntos en ${reto.minijuego}.`);
          this.formulario.reset({ nombreUsuarioRetado: '', minijuegoId: ''});
          this.cargarRetos();
        },
        error: (error: HttpErrorResponse) => {
          this.enviando.set(false);
          this.mensajeErrorEnvio.set(this.mensajeDe(error, 'No se pudo enviar el reto'));
        }
      });
  }

  esLanzado(reto: Retodtomodel): boolean {
    return reto.retador === this.miNombre;
  }

  private mensajeDe(error: HttpErrorResponse, mensajePorDefecto: string): string {
    if (error.status === 0) {
      return 'No se pudo conectar con el servidor';
    }
    const cuerpo = error.error;
    if (cuerpo?.error){
      return cuerpo.error;
    }
    if (error.status === 400 && cuerpo && typeof cuerpo === 'object'){
      return Object.values(cuerpo).join('. ');
    }
    return mensajePorDefecto;
  }
}
