import { Component, HostListener, OnDestroy, OnInit, signal } from '@angular/core';
import { Partidaservice } from '../../services/partidaservice';
import { HttpErrorResponse } from '@angular/common/http';

/** Minijuego de concurso de baile donde aparecerá una flecha y el jugador debe pulsar la misma
 * fehca del teclado. Los aciertos suman puntos y aumenta la racha, los fallos restan puntos y
 * reinicia la racha. Cada partida es de tiempo limitado, y al terminarla, se guarda el
 * puntaje */

interface Flecha {
  tecla: string;
  simbolo: string;
}

@Component({
  selector: 'app-concursobailecomponent',
  standalone: false,
  styleUrl: './concursobailecomponent.css',
  templateUrl: './concursobailecomponent.html',
})
export class Concursobailecomponent implements OnInit, OnDestroy {

  readonly flechas: Flecha[] = [
    { tecla: 'ArrowLeft', simbolo: '⬅️', },
    { tecla: 'ArrowRight', simbolo: '➡️', },
    { tecla: 'ArrowUp', simbolo: '⬆️', },
    { tecla: 'ArrowDown', simbolo: '⬇️', },
  ];

  readonly idMinijuego = 5;
  readonly duracionPartida = 30;
  readonly puntosPorAcierto = 10;
  readonly penalizacionFallo = 5;

  flechaActual = signal<Flecha>(this.flechas[0]);
  puntaje = signal(0);
  racha = signal(0);
  mejorRacha = signal(0);
  tiempoRestante = signal(this.duracionPartida);
  terminado = signal(false);
  ultimoResultado = signal<'acierto' | 'fallo' | null>(null);
  estadoGuardado = signal<'guardando' | 'guardado' | 'error' | null>(null);
  mensajeErrorGuardado = signal('');

  private temporizador?: ReturnType<typeof setInterval>;

  constructor(private partidaservice: Partidaservice) {}

  ngOnInit() {
    this.iniciarPartida();
  }
  /** Arranca la primera partida al mostrarse el componente */

  ngOnDestroy() {
    clearInterval(this.temporizador);
  }
  /** Detiene el cronometro al salir de la pantalla y asi no siga corriendo*/

  iniciarPartida(): void {

    clearInterval(this.temporizador);
    this.puntaje.set(0);
    this.racha.set(0);
    this.mejorRacha.set(0);
    this.tiempoRestante.set(this.duracionPartida);
    this.terminado.set(false);
    this.ultimoResultado.set(null);
    this.estadoGuardado.set(null);
    this.flechaActual.set(this.flechas [Math.floor(Math.random() * this.flechas.length)]);
    this.temporizador = setInterval(() => this.restarSegundo(), 1000);
  }
  /** Inicializa todo y arranca el cronometro */

  private restarSegundo(): void {
    this.tiempoRestante.update((segundos) => segundos -1);

    if (this.tiempoRestante() <= 0){
      this.terminarPartida();
    }
  }
  /** Resta un segundo al cronometro y termina la partida si acaba el tiempo */

  private terminarPartida(): void {

    clearInterval(this.temporizador);
    this.terminado.set(true);
    this.guardarPartida();
  }
  /** Finaliza la partida, deteniendo el cronometro, mostrando el panel final y guarda el puntaje */

  @HostListener('window:keydown', ['$event'])
  pulsarTecla(evento: KeyboardEvent): void {

    if (this.terminado() || evento.repeat){
      return;
    }

    if (!this.flechas.some((flecha) => flecha.tecla === evento.key)) {
      return;
    }
    evento.preventDefault();

    if (evento.key === this.flechaActual().tecla){
      this.puntaje.update((actual) => actual + this.puntosPorAcierto);
      this.racha.update((actual) => actual + 1);
      this.mejorRacha.update((mejor) => Math.max(mejor, this.racha()));
      this.ultimoResultado.set('acierto');
      this.elegirFlechaNueva();
    } else {
      this.puntaje.update((actual) => Math.max(0, actual - this.penalizacionFallo));
      this.racha.set(0);
      this.ultimoResultado.set('fallo');
    }
  }
  /** Revisa la flecha por el jugador, si esta coincide sumara puntos y racha y cambie de flecha,
   * si no restará puntos y reinicirá racha */

  private elegirFlechaNueva(): void {

    const actual = this.flechaActual();
    const candidatas = this.flechas.filter((flecha) => flecha.tecla !== actual.tecla);
    this.flechaActual.set(candidatas[Math.floor(Math.random() * candidatas.length)]);
  }
  /** Se elige una flecha distinta a la actual */

  guardarPartida(): void {
    this.estadoGuardado.set('guardando');
    this.mensajeErrorGuardado.set('');
    this.partidaservice.registrar({ minijuegoId: this.idMinijuego, puntaje: this.puntaje()})
      .subscribe({ next: () => this.estadoGuardado.set('guardado'),
        error: (error: HttpErrorResponse) => { this.estadoGuardado.set('error');
        this.mensajeErrorGuardado.set(error.status === 0 ? 'No se pudo conectar con el servidor'
        : (error.error?.error ?? 'No se pudo guardarla partida'));
        }});
  }
  /** El puntaje de la partida se envia al backend */
}
