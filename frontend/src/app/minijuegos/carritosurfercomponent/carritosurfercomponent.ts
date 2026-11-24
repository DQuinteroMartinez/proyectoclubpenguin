import { Component, computed, HostListener, OnDestroy, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Partidaservice } from '../../services/partidaservice';
import { HttpErrorResponse } from '@angular/common/http';



interface Objeto {
  id: number;
  tipo: 'hielo' | 'pez';
  x: number;
  altura: number;
}
/** Obstaculo que se acerca al carrito o un premio */

@Component({
  selector: 'app-carritosurfercomponent',
  standalone: false,
  styleUrl: './carritosurfercomponent.css',
  templateUrl: './carritosurfercomponent.html',
})
export class Carritosurfercomponent implements OnInit, OnDestroy {

  readonly anchoEscenario = 600;
  readonly altoEscenario = 300;
  readonly alturaSuelo = 40;
  readonly xCarrito = 60;
  readonly tamanoCarrito = 40;
  readonly tamanoObjeto = 30;
  readonly intervaloJuego = 30;
  readonly velocidadInicial = 6;
  readonly velocidadMaxima = 12;
  readonly aceleracion = 0.002;
  readonly velocidadSalto = 14;
  readonly gravedad = 0.8;
  readonly vidasIniciales = 3;
  readonly puntosPorPez = 50;
  readonly idMinijuego = 4;

  objetos = signal<Objeto[]>([]);
  alturaCarrito = signal(0);
  distancia = signal(0);
  peces = signal(0);
  vidas = signal(this.vidasIniciales);
  terminado = signal(false);
  puntaje = computed(() => Math.floor(this.distancia()) + this.peces() * this.puntosPorPez);
  estadoGuardado = signal<'guardando' | 'guardado' | 'error' | null>(null);
  mensajeErrorGuardado = signal('');

  private saliendo = false;
  private velocidad = this.velocidadInicial;
  private velocidadVertical = 0;
  private vueltasParaSiguiente = 0;
  private siguienteId = 0;
  private temporizador?: ReturnType<typeof setInterval>;

  constructor(private partidaservice: Partidaservice, private router: Router) {}

  ngOnInit(): void {
    this.iniciarPartida();
  }
  /** Arranca la partida al mostrarse el component*/

  ngOnDestroy(): void {
    clearInterval(this.temporizador);
    if (!this.terminado() && this.puntaje() > 0) {
      this.partidaservice.registrar({ minijuegoId: this.idMinijuego, puntaje: this.puntaje() })
        .subscribe({ error: () => { /* al salir no se puede mostrar el error, se ignora */ } });
    }
  }
  /** Detiene el bucle al salir de la pantalla y guarda la partida si estaba a medias con puntos */

  iniciarPartida(): void {

    clearInterval(this.temporizador);
    this.objetos.set([]);
    this.alturaCarrito.set(0);
    this.distancia.set(0);
    this.peces.set(0);
    this.vidas.set(this.vidasIniciales);
    this.terminado.set(false);
    this.estadoGuardado.set(null);
    this.saliendo = false;
    this.velocidad = this.velocidadInicial;
    this.velocidadVertical = 0;
    this.vueltasParaSiguiente = this.vueltasAleatorias();
    this.temporizador = setInterval(() => this.actualizarJuego(), this.intervaloJuego);

  }
  /** Inicializa todo y arranca el bucle del juego. Su uso es al abrir la pantalla y al pulsar
   * 'Jugar de nuevo' */

  private actualizarJuego(): void {

    let altura = this.alturaCarrito() + this.velocidadVertical;
    this.velocidadVertical -= this.gravedad;

    if (altura <= 0){
      altura = 0;
      this.velocidadVertical = 0;
    }
    this.alturaCarrito.set(altura);
    this.velocidad = Math.min(this.velocidadMaxima, this.velocidad + this.aceleracion);
    this.distancia.update((actual) => actual + this.velocidad/10);

    let choques = 0;
    const restantes: Objeto[] = [];

    for (const objeto of this.objetos()) {
      const movido = {...objeto, x: objeto.x - this.velocidad };
      if (this.tocaElCarrito(movido, altura)) {
        if (movido.tipo === 'pez'){
          this.peces.update((actual) => actual + 1);
        } else {
          choques++;
        }
      } else if (movido.x + this.tamanoObjeto > 0){
        restantes.push(movido);
      }
    }
    this.vueltasParaSiguiente--;
    if (this.vueltasParaSiguiente <= 0) {
      restantes.push(this.crearObjeto());
      this.vueltasParaSiguiente = this.vueltasAleatorias();
    }
    this.objetos.set(restantes);

    if (choques > 0) {
      this.vidas.update((actual) => Math.max(0, actual - choques));
      if (this.vidas() === 0){
        this.terminarPartida();
      }
    }
  }
  /** Da una vuelta del juego, aplicando salto, aceleracion, movimiento de elementos, resuelve las
   * colisiones y crea elementos nuevos */

  private tocaElCarrito(objeto: Objeto, alturaActual: number): boolean {

    const solapaHorizontal = objeto.x < this.xCarrito + this.tamanoCarrito &&
      objeto.x + this.tamanoObjeto > this.xCarrito;
    const solapaVertical = alturaActual < objeto.altura + this.tamanoObjeto &&
      alturaActual + this.tamanoCarrito > objeto.altura;
    return solapaHorizontal && solapaVertical;
  }
  /** Indica si un elemento esta tocando el carrito */

  private crearObjeto(): Objeto {

    const esPez = Math.random() < 0.35;
    return { id: this.siguienteId++, tipo: esPez ? 'pez' : 'hielo', x: this.anchoEscenario,
    altura: esPez && Math.random() < 0.5 ? 90: 0 }

  }
  /** Crea un nuevo elemento al borde derecho del escenario */

  private vueltasAleatorias(): number {
    return 40 + Math.floor(Math.random() * 40);
  }
  /** Calcula aleatoriamente cuantas vueltas esperar para crear el sig elemento*/

  private terminarPartida(): void {
    clearInterval(this.temporizador);
    this.terminado.set(true);
    this.guardarPartida();

  }
  /** finaliza la partida, deteniendo el bucle, muestra el panel de fin y guarda el puntaje */

  @HostListener('window:keydown', ['$event'])
  saltar (evento: KeyboardEvent): void {
    if (evento.key !== ' ' && evento.key !== 'ArrowUp'){
      return;
    }
    evento.preventDefault();
    if (!this.terminado() && this.alturaCarrito() === 0 ){
      this.velocidadVertical = this.velocidadSalto;
    }
  }
  /** Hace saltar el carrito con la barra espaciadora o con flecha arriba */

  corazones(): string {
    return '❤️'.repeat(this.vidas());
  }
  /** Devuelve las vidas restantes como corazones, motrandolas en el marcador */

  salir(): void {
    if (this.estadoGuardado() === 'guardado') {
      this.router.navigate(['/minijuegos']);
      return;
    }
    this.saliendo = true;
    if (!this.terminado()) {
      clearInterval(this.temporizador);
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

  guardarPartida(): void {

    this.estadoGuardado.set('guardando');
    this.mensajeErrorGuardado.set('');
    this.partidaservice.registrar( { minijuegoId: this.idMinijuego, puntaje: this.puntaje() })
      .subscribe({
        next: () => {
          this.estadoGuardado.set('guardado');
          if (this.saliendo) {
            this.router.navigate(['/minijuegos']);
          }
        },
        error: (error: HttpErrorResponse) => {
          this.estadoGuardado.set('error');
          this.mensajeErrorGuardado.set(error.status === 0 ? 'No se pudo conectar con el servidor'
          : (error.error?.error ?? 'No se pudo guardar la partida'));
        }
      });
  }
  /** Envía el puntaje de la partida del backend */

}
