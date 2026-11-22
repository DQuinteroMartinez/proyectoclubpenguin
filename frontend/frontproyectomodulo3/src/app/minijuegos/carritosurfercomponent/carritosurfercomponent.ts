import { Component, computed, HostListener, OnDestroy, OnInit, signal } from '@angular/core';
import { Partidaservice } from '../../services/partidaservice';
import { HttpErrorResponse } from '@angular/common/http';


interface Objeto {
  id: number;
  tipo: 'hielo' | 'pez';
  x: number;
  altura: number;
}

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

  private velocidad = this.velocidadInicial;
  private velocidadVertical = 0;
  private vueltasParaSiguiente = 0;
  private siguienteId = 0;
  private temporizador?: ReturnType<typeof setInterval>;

  constructor(private partidaservie: Partidaservice) {}

  ngOnInit(): void {
    this.iniciarPartida();
  }

  ngOnDestroy(): void {
    clearInterval(this.temporizador);
  }

  iniciarPartida(): void {

    clearInterval(this.temporizador);
    this.objetos.set([]);
    this.alturaCarrito.set(0);
    this.distancia.set(0);
    this.peces.set(0);
    this.vidas.set(this.vidasIniciales);
    this.terminado.set(false);
    this.estadoGuardado.set(null);
    this.velocidad = this.velocidadInicial;
    this.velocidadVertical = 0;
    this.vueltasParaSiguiente = this.vueltasAleatorias();
    this.temporizador = setInterval(() => this.actualizarJuego(), this.intervaloJuego);

  }

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

  private tocaElCarrito(objeto: Objeto, alturaActual: number): boolean {

    const solapaHorizontal = objeto.x < this.xCarrito + this.tamanoCarrito &&
      objeto.x + this.tamanoObjeto > this.xCarrito;
    const solapaVertical = alturaActual < objeto.altura + this.tamanoObjeto &&
      alturaActual + this.tamanoCarrito > objeto.altura;
    return solapaVertical && solapaVertical;
  }

  private crearObjeto(): Objeto {

    const esPez = Math.random() < 0.35;
    return { id: this.siguienteId++, tipo: esPez ? 'pez' : 'hielo', x: this.anchoEscenario,
    altura: esPez && Math.random() < 0.5 ? 90: 0 }

  }

  private vueltasAleatorias(): number {
    return 40 + Math.floor(Math.random() * 40);
  }

  private terminarPartida(): void {
    clearInterval(this.temporizador);
    this.terminado.set(true);
    this.guardarPartida();

  }

  @HostListener('window:resize', ['$event'])
  saltar (evento: KeyboardEvent): void {
    if (evento.key !== ' ' && evento.key !== 'ArrowUp'){
      return;
    }
    evento.preventDefault();
    if (!this.terminado() && this.alturaCarrito() === 0 ){
      this.velocidadVertical = this.velocidadSalto;
    }
  }

  corazones(): string {
    return '❤️'.repeat(this.vidas());
  }

  guardarPartida(): void {

    this.estadoGuardado.set('guardando');
    this.mensajeErrorGuardado.set('');
    this.partidaservie.registrar( { minijuegoId: this.idMinijuego, puntaje: this.puntaje() })
      .subscribe({
        next: () => this.estadoGuardado.set('guardado'),
        error: (error: HttpErrorResponse) => {
          this.estadoGuardado.set('error');
          this.mensajeErrorGuardado.set(error.status === 0 ? 'No se pudo conectar con el servidor'
          : (error.error?.error ?? 'No se pudo guardar la partida'));
        }
      });
  }

}
