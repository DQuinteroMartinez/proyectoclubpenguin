import { Component, HostListener, OnDestroy, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Partidaservice } from '../../services/partidaservice';
import { HttpErrorResponse } from '@angular/common/http';

/**  Este es uno de los minijuegos. El jugador mueve una canasta con las flechas del teclado
 * para atrapar bolsas que caen */

interface Bolsa{
  id: number;
  x: number;
  y: number;
}

@Component({
  selector: 'app-atrapabolsascomponent',
  standalone: false,
  styleUrl: './atrapabolsascomponent.css',
  templateUrl: './atrapabolsascomponent.html',
})
export class Atrapabolsascomponent implements OnInit, OnDestroy {

  readonly idMinijuego = 2;
  readonly anchoEscenario = 600;
  readonly altoEscenario = 400;
  readonly anchoCanasta = 70;
  readonly altoCanasta = 40;
  readonly paso = 8;
  readonly tamanoBolsa = 30;
  readonly velocidadCaida = 4;
  readonly intervaloJuego = 30;
  readonly vueltasPorBolsa = 30;
  readonly puntosPorBolsa = 10;
  readonly duracionPartida = 30;
  readonly xInicial = (this.anchoEscenario - this.anchoCanasta) /2;

  x = signal(this.xInicial);
  bolsas = signal<Bolsa[]>([]);
  puntaje = signal(0);
  tiempoRestante = signal(this.duracionPartida);
  terminado = signal(false);
  estadoGuardado = signal<'guardando' | 'guardado' | 'error' | null>(null);
  mensajeErrorGuardado = signal('');

  private contadorVueltas = 0;
  private saliendo = false;
  private moviendoIzquierda = false;
  private moviendoDerecha = false;
  private siguienteId  = 0;
  private temporizador?: ReturnType<typeof setInterval>;
  private temporizadorTiempo?: ReturnType<typeof setInterval>;

  constructor (private partidaservice: Partidaservice, private router: Router){}

  ngOnInit(): void{
    this.iniciarPartida();
  }
  /** Este metodo arranca el bucle del juego al mostrarse el componente */

  ngOnDestroy(): void{
    this.detenerRelojes();
    if (!this.terminado() && this.puntaje() > 0) {
      this.partidaservice.registrar({ minijuegoId: this.idMinijuego, puntaje: this.puntaje() })
        .subscribe({ error: () => { /* al salir no se puede mostrar el error, se ignora */ } });
    }
  }
  /** Este metodo detiene el bucle del juego al salir de la pantalla. Si había una partida a medias
   * con puntos, la guarda para no perder lo jugado */

  iniciarPartida(): void{
    this.detenerRelojes();
    this.puntaje.set(0);
    this.bolsas.set([]);
    this.tiempoRestante.set(this.duracionPartida);
    this.terminado.set(false);
    this.estadoGuardado.set(null);
    this.contadorVueltas = 0;
    this.x.set(this.xInicial);
    this.saliendo = false;
    this.temporizador = setInterval(() => this.actualizarJuego(), this.intervaloJuego);
    this.temporizadorTiempo= setInterval(() => this.restarSegundo(), 1000);
  }
  /** Pone todo en estado inicial y arranca los dos relojes */

  private restarSegundo(): void {
    this.tiempoRestante.update((segundos) => segundos -1);
    if (this.tiempoRestante() <= 0){
      this.terminarPartida();
    }
  }
  /** Resta un segundo al cronometro y termina la partida si se acaba el tiempo*/

  private terminarPartida(): void {
    this.detenerRelojes();
    this.terminado.set(true);
    this.guardarPartida();
  }
  /** Permite terminar la partida deteniendo los relojes y muestra el panel de fin */

  guardarPartida(): void {
    this.estadoGuardado.set('guardando');
    this.mensajeErrorGuardado.set('');
    this.partidaservice.registrar({ minijuegoId: this.idMinijuego, puntaje: this.puntaje() }).subscribe({
      next: () => {
        this.estadoGuardado.set('guardado');
        if (this.saliendo) {
          this.router.navigate(['/minijuegos']);
        }
      },
      error: (error: HttpErrorResponse) => {
        this.estadoGuardado.set('error');
        this.mensajeErrorGuardado.set(error.status === 0 ? 'No se pudo conectar con el servidor' :
          (error.error?.error ?? 'No se pudo guardar la partida'));
      },
    });
  }

  salir(): void {
    if (this.estadoGuardado() === 'guardado') {
      this.router.navigate(['/minijuegos']);
      return;
    }
    this.saliendo = true;
    if (!this.terminado()) {
      this.detenerRelojes();
      this.terminado.set(true);
    }
    if (this.estadoGuardado() !== 'guardando') {
      this.guardarPartida();
    }
  }
  /** Permite salir del juego guardando lo jugado. Detiene la partida, envía el puntaje y, cuando
   * el backend confirma, vuelve al catálogo. Si falla, se muestra el error para reintentar. */

  salirSinGuardar(): void {
    this.router.navigate(['/minijuegos']);
  }
  /** Sale al catálogo sin guardar (por si no hay conexión con el servidor). */

  private detenerRelojes(): void{
    clearInterval(this.temporizador);
    clearInterval(this.temporizadorTiempo);
  }
  /** Detiene el bucle del juego y el cronometro de segundos */

  private actualizarJuego(): void{

    this.contadorVueltas++;
    this.moverCanasta();
    const bajadas = this.bolsas().map((bolsa) => ({ ...bolsa, y:bolsa.y + this.velocidadCaida}));
    const atrapadas = bajadas.filter ((bolsa) => this.chocaConCanasta(bolsa));
    const lista = bajadas.filter((bolsa) => !this.chocaConCanasta(bolsa)
    && bolsa.y < this.altoEscenario);

    if (atrapadas.length > 0) {
      this.puntaje.update((actual) => actual + atrapadas.length * this.puntosPorBolsa);
    }

    if (this.contadorVueltas %  this.vueltasPorBolsa === 0) {
      lista.push({ id: this.siguienteId++, x: Math.floor(Math.random() * (this.anchoEscenario - this.tamanoBolsa)),
        y: -this.tamanoBolsa });
    }
    this.bolsas.set(lista);
  }
  /** Hace una vuelta del juego, es decir, baja las bolsas, quita las que llegaron hasta abajo y
   * crea una bolsa nueva cada cierto numero de vueltas */

  private chocaConCanasta(bolsa: Bolsa): boolean {
    const arribaCanasta = this.altoEscenario -this.altoCanasta;
    const llegoALaAltura = bolsa.y + this.tamanoBolsa >= arribaCanasta;
    const solapaEnHorizontal = bolsa.x < this.x() + this.anchoCanasta && bolsa.x + this.tamanoBolsa > this.x();

    return llegoALaAltura && solapaEnHorizontal;
  }
  /** Este metodo dice si una bolsa esta tocando la canasta. Indica que hay choque cuando la bolsa
   * llega a la alruta de la canasta y se solapan de forma horizontal*/

  @HostListener('window:keydown', ['$event'])
  teclaPulsada(evento: KeyboardEvent): void {
    if (evento.key === 'ArrowLeft') {
      this.moviendoIzquierda = true;
    } else if (evento.key === 'ArrowRight') {
      this.moviendoDerecha = true;
    } else {
      return;
    }
    evento.preventDefault();
  }
  /** Recuerda qué flecha está pulsada; el movimiento real ocurre en cada vuelta del juego, así
   * la canasta se desliza suave en vez de saltar con la repetición del teclado. */

  @HostListener('window:keyup', ['$event'])
  teclaSoltada(evento: KeyboardEvent): void {
    if (evento.key === 'ArrowLeft') {
      this.moviendoIzquierda = false;
    } else if (evento.key === 'ArrowRight') {
      this.moviendoDerecha = false;
    }
  }
  /** Deja de mover la canasta cuando se suelta la flecha. */

  private moverCanasta(): void {
    const maximoX = this.anchoEscenario - this.anchoCanasta;
    if (this.moviendoIzquierda) {
      this.x.update((actual) => Math.max(0, actual - this.paso));
    }
    if (this.moviendoDerecha) {
      this.x.update((actual) => Math.min(maximoX, actual + this.paso));
    }
  }
  /** Mueve la canasta de izquierda a derecha según las flechas pulsadas, sin salirse del área. */
}
