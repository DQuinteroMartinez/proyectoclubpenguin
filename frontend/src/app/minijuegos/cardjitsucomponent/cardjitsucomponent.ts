import { Component, computed, OnDestroy, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Partidaservice } from '../../services/partidaservice';
import { HttpErrorResponse } from '@angular/common/http';

/**
 * Minijuego Card-Jitsu. El jugador y un rival juegan una carta por ronda.
 * Gana la ronda quien tenga el elemento que vence al otro (o el mayor poder si son iguales) y
 * colecciona ese elemento. Gana el combate quien reúna los tres elementos distintos. Al terminar
 * se guarda el puntaje. */


type Elemento = 'fuego' | 'agua' | 'nieve';
/** Elemento de una carta. Fuego vence a nieve, nieve vence a agua y agua vence a fuego. */

interface Carta {
  id: number;
  elemento: Elemento;
  poder: number;
}
/** Carta del juego. */

@Component({
  selector: 'app-cardjitsucomponent',
  standalone: false,
  styleUrl: './cardjitsucomponent.css',
  templateUrl: './cardjitsucomponent.html',
})
export class Cardjitsucomponent implements OnInit, OnDestroy {

  readonly elementos: Elemento[] = ['fuego', 'agua', 'nieve'];
  readonly simbolos: Record<Elemento, string> = { fuego: '🔥', agua: '💧', nieve: '❄️' };
  readonly vence: Record<Elemento, Elemento> = { fuego: 'nieve', nieve: 'agua', agua: 'fuego' };
  readonly idMinijuego = 1;
  readonly cartasEnMano = 5;
  readonly maximoRondas = 12;
  readonly puntosPorRonda = 20;
  readonly bonusVictoria = 100;

  mano = signal<Carta[]>([]);
  cartaJugador = signal<Carta | null>(null);
  cartaRival = signal<Carta | null>(null);
  resultado = signal<'gana' | 'pierde' | 'empate' | null>(null);
  coleccionJugador = signal<Elemento[]>([]);
  coleccionRival = signal<Elemento[]>([]);
  ronda = signal(0);
  rondasGanadas = signal(0);
  ganador = signal<'jugador' | 'rival' | 'empate' | null>(null);
  terminado = computed(() => this.ganador() !== null);
  puntaje = computed(
    () => this.rondasGanadas() * this.puntosPorRonda + (this.ganador() === 'jugador' ? this.bonusVictoria : 0)
  );
  estadoGuardado = signal<'guardando' | 'guardado' | 'error' | null>(null);
  mensajeErrorGuardado = signal('');
  private siguienteId = 0;
  private saliendo = false;

  constructor(private partidaservice: Partidaservice, private router: Router) {}

  ngOnInit(): void {
    this.iniciarPartida();
  }
  /** Arranca la primera partida al mostrarse el componente. */

  ngOnDestroy(): void {
    if (!this.terminado() && this.rondasGanadas() > 0) {
      this.partidaservice.registrar({ minijuegoId: this.idMinijuego, puntaje: this.puntaje() })
        .subscribe({ error: () => { /* al salir no se puede mostrar el error, se ignora */ } });
    }
  }
  /** Al salir de la pantalla con un combate a medias y rondas ganadas, guarda el puntaje. */

  iniciarPartida(): void {
    this.saliendo = false;
    this.mano.set(Array.from({ length: this.cartasEnMano }, () => this.crearCarta()));
    this.cartaJugador.set(null);
    this.cartaRival.set(null);
    this.resultado.set(null);
    this.coleccionJugador.set([]);
    this.coleccionRival.set([]);
    this.ronda.set(0);
    this.rondasGanadas.set(0);
    this.ganador.set(null);
    this.estadoGuardado.set(null);
  }
  /** Pone todo en su estado inicial y reparte la mano. Se usa al abrir la pantalla y al pulsar
   * "Jugar de nuevo". */

  private crearCarta(): Carta {
    return { id: this.siguienteId++,
      elemento: this.elementos[Math.floor(Math.random() * this.elementos.length)],
      poder: 2 + Math.floor(Math.random() * 11),
    };
  }
  /** Crea una carta con elemento y poder al azar. */

  private comparar(a: Carta, b: Carta): number {
    if (a.elemento === b.elemento) {
      return Math.sign(a.poder - b.poder);
    }
    return this.vence[a.elemento] === b.elemento ? 1 : -1;
  }
  /** Compara dos cartas con elementos distintos gana la que vence según la tabla; con el mismo
   * elemento, la de mayor poder */

  jugarCarta(carta: Carta): void {
    if (this.terminado()) {
      return;
    }

    const cartaRival = this.crearCarta();
    const comparacion = this.comparar(carta, cartaRival);

    this.cartaJugador.set(carta);
    this.cartaRival.set(cartaRival);
    this.ronda.update((actual) => actual + 1);

    if (comparacion > 0) {
      this.rondasGanadas.update((actual) => actual + 1);
      this.coleccionJugador.update((lista) =>
        lista.includes(carta.elemento) ? lista : [...lista, carta.elemento]
      );
      this.resultado.set('gana');
    } else if (comparacion < 0) {
      this.coleccionRival.update((lista) =>
        lista.includes(cartaRival.elemento) ? lista : [...lista, cartaRival.elemento]
      );
      this.resultado.set('pierde');
    } else {
      this.resultado.set('empate');
    }

    this.mano.update((lista) => lista.map((c) => (c.id === carta.id ? this.crearCarta() : c)));
    this.revisarFinal();
  }
  /** Se ejecuta cuando el jugador hace clic en una carta de su mano. El rival juega una carta al
   * azar, se decide la ronda, se reponen las cartas y se revisa si el combate terminó. */

  private revisarFinal(): void {
    const delJugador = this.coleccionJugador().length;
    const delRival = this.coleccionRival().length;

    if (delJugador === this.elementos.length) {
      this.ganador.set('jugador');
    } else if (delRival === this.elementos.length) {
      this.ganador.set('rival');
    } else if (this.ronda() >= this.maximoRondas) {
      this.ganador.set(delJugador > delRival ? 'jugador' : delJugador < delRival ? 'rival' : 'empate');
    }

    if (this.terminado()) {
      this.guardarPartida();
    }
  }
  /** Revisa si alguien reunió los tres elementos o si se llegó al máximo de rondas. En ese caso
   * termina el combate y guarda el puntaje. */

  tieneJugador(elemento: Elemento): boolean {
    return this.coleccionJugador().includes(elemento);
  }
  /** Indica si el jugador ya coleccionó un elemento. */

  tieneRival(elemento: Elemento): boolean {
    return this.coleccionRival().includes(elemento);
  }
  /** Indica si el rival ya coleccionó un elemento. */

  textoResultado(): string {
    switch (this.resultado()) {
      case 'gana':
        return '¡Ganaste la ronda!';
      case 'pierde':
        return 'El rival ganó la ronda.';
      case 'empate':
        return 'Empate: nadie se lleva el elemento.';
      default:
        return '';
    }
  }
  /** Devuelve el texto del resultado de la última ronda. */

  textoFinal(): string {
    switch (this.ganador()) {
      case 'jugador':
        return '¡Ganaste el combate!';
      case 'rival':
        return 'El rival ganó el combate';
      case 'empate':
        return 'El combate terminó en empate';
      default:
        return '';
    }
  }
  /** Devuelve el texto del resultado final del combate. */

  salir(): void {
    if (this.estadoGuardado() === 'guardado') {
      this.router.navigate(['/minijuegos']);
      return;
    }
    this.saliendo = true;
    if (!this.terminado()) {
      this.ganador.set('empate');
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

  guardarPartida(): void {
    this.estadoGuardado.set('guardando');
    this.mensajeErrorGuardado.set('');

    this.partidaservice
      .registrar({ minijuegoId: this.idMinijuego, puntaje: this.puntaje() })
      .subscribe({
        next: () => {
          this.estadoGuardado.set('guardado');
          if (this.saliendo) {
            this.router.navigate(['/minijuegos']);
          }
        },
        error: (error: HttpErrorResponse) => {
          this.estadoGuardado.set('error');
          this.mensajeErrorGuardado.set(
            error.status === 0
              ? 'No se pudo conectar con el servidor'
              : (error.error?.error ?? 'No se pudo guardar la partida')
          );
        },
      });
  }
  /** Envía el puntaje de la partida al backend. Si falla, deja el mensaje de error para que el
   * jugador pueda reintentar. */
}
