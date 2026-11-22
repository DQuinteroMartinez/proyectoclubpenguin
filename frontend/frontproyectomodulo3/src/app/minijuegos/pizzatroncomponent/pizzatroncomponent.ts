import { Component, OnDestroy, OnInit, signal } from '@angular/core';
import { Partidaservice } from '../../services/partidaservice';
import { HttpErrorResponse } from '@angular/common/http';

interface Ingrediente {
  id: string;
  nombre: string;
  simbolo: string;

}

@Component({
  selector: 'app-pizzatroncomponent',
  standalone: false,
  styleUrl: './pizzatroncomponent.css',
  templateUrl: './pizzatroncomponent.html',
})
export class Pizzatroncomponent implements OnInit, OnDestroy {

  readonly ingredientes: Ingrediente[] = [
    { id: 'salsa', nombre: 'Salsa', simbolo: ' '},
    { id: 'queso', nombre: 'Queso', simbolo: ''},
    { id: 'champinon', nombre: 'Champiñón', simbolo: ' '},
    { id: 'pina', nombre: 'Piña', simbolo: ' '},
    { id: 'pimiento', nombre:'Pimiento', simbolo:' '},
  ];

  readonly idMinijuego = 3;
  readonly duracionPartida = 45;
  readonly puntosPorIngrediente = 10;
  readonly penalizacionError = 5;

  pedido = signal<Ingrediente[]>([]);
  agregados = signal<Ingrediente[]>([]);
  puntaje = signal(0);
  pizzasCompletadas = signal(0);
  tiempoRestante = signal(this.duracionPartida);
  terminado = signal(false);
  mensaje = signal('');
  estadoGuardado = signal<'guardando' | 'guardado' | 'error' | null>(null);
  mensajeErrorGuardado = signal('');

  private temporizador?: ReturnType<typeof setInterval>;

  constructor(private partidaservice: Partidaservice) {}

  ngOnInit() {
    this.iniciarPartida();
  }

  ngOnDestroy() {
    clearInterval(this.temporizador);
  }

  iniciarPartida() {
    clearInterval(this.temporizador);
    this.pizzasCompletadas.set(0);
    this.tiempoRestante.set(this.duracionPartida);
    this.terminado.set(false);
    this.mensaje.set('');
    this.estadoGuardado.set(null);
    this.agregados.set([]);
    this.pedido.set(this.crearPedido());

    this.temporizador = setInterval(() => this.restarSegundo(), 1000);
  }


  private restarSegundo(): void {
    this.tiempoRestante.update((segundos) => segundos - 1);
    if (this.tiempoRestante() <= 0) {
      this.terminarPartida();
    }
  }
  /** Resta un segundo al cronómetro y termina la partida si se acaba el tiempo. */

  private terminarPartida(): void {
    clearInterval(this.temporizador);
    this.terminado.set(true);
    this.guardarPartida();
  }
  /** Termina la partida, deteniendo el cronómetro, muestra el panel de fin y guarda el puntaje. */

  private crearPedido(): Ingrediente[] {
    const cantidad = 2 + Math.floor(Math.random() * 3);
    return [...this.ingredientes].sort(() => Math.random() - 0.5).slice(0, cantidad);
  }
  /** Crea un pedido nuevo con entre 2 y 4 ingredientes distintos elegidos al azar. */


  estaAgregado(ingrediente: Ingrediente): boolean {
    return this.agregados().some((agregado) => agregado.id === ingrediente.id);
  }
  /** Indica si un ingrediente ya está puesto en la pizza actual. */

  agregar(ingrediente: Ingrediente): void {
    if (this.terminado()) {
      return;
    }

    const loPideElPedido = this.pedido().some((pedido) => pedido.id === ingrediente.id);
    if (!loPideElPedido || this.estaAgregado(ingrediente)) {
      this.puntaje.update((actual) => Math.max(0, actual - this.penalizacionError));
      this.agregados.set([]);
      this.mensaje.set('¡Esa pizza se arruinó! Empieza otra.');
      return;
    }

    this.agregados.update((lista) => [...lista, ingrediente]);
    this.mensaje.set('');

    if (this.agregados().length === this.pedido().length) {
      this.puntaje.update((actual) => actual + this.pedido().length * this.puntosPorIngrediente);
      this.pizzasCompletadas.update((actual) => actual + 1);
      this.mensaje.set('¡Pizza lista!');
      this.agregados.set([]);
      this.pedido.set(this.crearPedido());
    }
  }
  /** Se ejecuta cuando el jugador hace clic en un ingrediente. Si el ingrediente está en el pedido
   *  y todavía no se puso, se agrega a la pizza, si no, la pizza se arruina. Al completar el
   *  pedido se suman los puntos y llega uno nuevo. */

  guardarPartida(): void {
    this.estadoGuardado.set('guardando');
    this.mensajeErrorGuardado.set('');

    this.partidaservice
      .registrar({ minijuegoId: this.idMinijuego, puntaje: this.puntaje() })
      .subscribe({
        next: () => this.estadoGuardado.set('guardado'),
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
  /** Envía el puntaje de la partida al backend. */
}

