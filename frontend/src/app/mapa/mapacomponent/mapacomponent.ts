import { Component, computed, HostListener, OnDestroy, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Autenticacionservice } from '../../services/autenticacionservice';
import { Posicionservice } from '../../services/posicionservice';

/** Edificio del mapa al que el pingüino puede entrar para ir a un minijuego o a otra pantalla. */
interface Zona {
  nombre: string;
  emoji: string;
  x: number;
  y: number;
  ancho: number;
  alto: number;
  ruta: string;
  color: string;
}

/** Aquí se encuentra la pantalla del mapa de exploración. Muestra el escenario con sus edificios
 * y el pingüino del jugador, que se mueve con las flechas (o WASD) de forma continua. Al acercarse
 * a un edificio se puede entrar con Enter o con un clic. La última posición se guarda en el
 * backend cuando el pingüino se detiene y al salir de la pantalla. */

@Component({
  selector: 'app-mapacomponent',
  standalone: false,
  styleUrl: './mapacomponent.css',
  templateUrl: './mapacomponent.html',
})
export class Mapacomponent implements OnInit, OnDestroy {

  readonly anchoEscenario = 800;
  readonly altoEscenario = 500;
  readonly tamanoPinguino = 40;
  readonly velocidad = 4;
  readonly intervaloJuego = 16;
  readonly esperaGuardado = 800;

  readonly zonas: Zona[] = [
    { nombre: 'Dojo Card-Jitsu', emoji: '⛩️', x: 50, y: 50, ancho: 130, alto: 90, ruta: '/minijuegos/card-jitsu', color: '#ffd6d6' },
    { nombre: 'Atrapabolsas', emoji: '🛍️', x: 335, y: 40, ancho: 130, alto: 90, ruta: '/minijuegos/atrapabolsas', color: '#d6f0ff' },
    { nombre: 'Pizzatrón 3000', emoji: '🍕', x: 620, y: 50, ancho: 130, alto: 90, ruta: '/minijuegos/pizzatron', color: '#fff0c2' },
    { nombre: 'Carrito Surfero', emoji: '🛒', x: 50, y: 360, ancho: 130, alto: 90, ruta: '/minijuegos/carrito-surfero', color: '#d9f7e0' },
    { nombre: 'Concurso de Baile', emoji: '🎶', x: 620, y: 360, ancho: 130, alto: 90, ruta: '/minijuegos/concurso-baile', color: '#ead9ff' },
    { nombre: 'Rankings', emoji: '🏆', x: 335, y: 380, ancho: 130, alto: 90, ruta: '/rankings', color: '#fff7b8' },
  ];

  x = signal((this.anchoEscenario - this.tamanoPinguino) / 2);
  y = signal((this.altoEscenario - this.tamanoPinguino) / 2);
  mirandoIzquierda = signal(false);
  esAdmin = signal(false);

  /** Zona sobre la que está el pingüino ahora mismo (o null si no está cerca de ninguna). */
  zonaCercana = computed<Zona | null>(() => {
    const margen = 12;
    const px = this.x();
    const py = this.y();
    return this.zonas.find((zona) =>
      px < zona.x + zona.ancho + margen &&
      px + this.tamanoPinguino > zona.x - margen &&
      py < zona.y + zona.alto + margen &&
      py + this.tamanoPinguino > zona.y - margen
    ) ?? null;
  });

  private teclas = new Set<string>();
  private temporizador?: ReturnType<typeof setInterval>;
  private temporizadorGuardado?: ReturnType<typeof setTimeout>;
  private hayCambioSinGuardar = false;

  constructor(
    private autenticacion: Autenticacionservice,
    private posicionservice: Posicionservice,
    private router: Router,
  ) {
    this.esAdmin.set(this.autenticacion.esAdmin());
  }

  ngOnInit(): void {
    this.cargarPosicion();
    this.temporizador = setInterval(() => this.actualizarMovimiento(), this.intervaloJuego);
  }
  /** Al mostrarse la pantalla se recupera la última posición y se arranca el bucle de movimiento */

  ngOnDestroy(): void {
    clearInterval(this.temporizador);
    clearTimeout(this.temporizadorGuardado);
    if (this.hayCambioSinGuardar) {
      this.guardarPosicion();
    }
  }
  /** Al salir de la pantalla se detiene el bucle y se guarda la posición si faltaba guardarla */

  private cargarPosicion(): void {
    this.posicionservice.obtener().subscribe({
      next: (posicion) => {
        if (posicion.x === 0 && posicion.y === 0) {
          return;
        }
        this.x.set(this.limitar(posicion.x, this.anchoEscenario - this.tamanoPinguino));
        this.y.set(this.limitar(posicion.y, this.altoEscenario - this.tamanoPinguino));
      },
      error: () => {
        // Si no se puede recuperar la posición, el pingüino simplemente empieza en el centro.
      },
    });
  }
  /** Pide la última posición guardada. Si el backend devuelve (0, 0) el jugador nunca se ha
   * movido, así que se deja el centro del mapa. */

  private guardarPosicion(): void {
    this.hayCambioSinGuardar = false;
    this.posicionservice
      .guardar({ x: Math.round(this.x()), y: Math.round(this.y()) })
      .subscribe({ error: () => { /* guardar la posición no es crítico, se ignora el fallo */ } });
  }
  /** Envía la posición actual al backend (enteros, como pide el contrato). */

  private programarGuardado(): void {
    this.hayCambioSinGuardar = true;
    clearTimeout(this.temporizadorGuardado);
    this.temporizadorGuardado = setTimeout(() => this.guardarPosicion(), this.esperaGuardado);
  }
  /** Espera a que el pingüino se detenga para guardar, así no se envía una petición por píxel. */

  private limitar(valor: number, maximo: number): number {
    return Math.min(maximo, Math.max(0, valor));
  }
  /** Mantiene un valor entre 0 y el máximo para que el pingüino no salga del escenario. */

  private actualizarMovimiento(): void {
    let dx = 0;
    let dy = 0;

    if (this.teclas.has('arrowleft') || this.teclas.has('a')) { dx -= 1; }
    if (this.teclas.has('arrowright') || this.teclas.has('d')) { dx += 1; }
    if (this.teclas.has('arrowup') || this.teclas.has('w')) { dy -= 1; }
    if (this.teclas.has('arrowdown') || this.teclas.has('s')) { dy += 1; }

    if (dx === 0 && dy === 0) {
      return;
    }

    const largo = Math.sqrt(dx * dx + dy * dy);
    const nuevoX = this.limitar(this.x() + (dx / largo) * this.velocidad, this.anchoEscenario - this.tamanoPinguino);
    const nuevoY = this.limitar(this.y() + (dy / largo) * this.velocidad, this.altoEscenario - this.tamanoPinguino);

    this.x.set(nuevoX);
    this.y.set(nuevoY);
    if (dx !== 0) {
      this.mirandoIzquierda.set(dx < 0);
    }
    this.programarGuardado();
  }
  /** Se ejecuta cada pocos milisegundos y mueve el pingüino según las teclas que están pulsadas.
   * En diagonal se normaliza la velocidad para que no avance más rápido. */

  @HostListener('window:keydown', ['$event'])
  teclaPulsada(evento: KeyboardEvent): void {
    const tecla = evento.key.toLowerCase();

    if (['arrowleft', 'arrowright', 'arrowup', 'arrowdown', 'a', 'd', 'w', 's'].includes(tecla)) {
      this.teclas.add(tecla);
      evento.preventDefault();
      return;
    }

    if ((tecla === 'enter' || tecla === ' ') && this.zonaCercana()) {
      evento.preventDefault();
      this.entrar(this.zonaCercana()!);
    }
  }
  /** Registra las teclas de movimiento pulsadas y permite entrar al edificio cercano con
   * Enter o la barra espaciadora. */

  @HostListener('window:keyup', ['$event'])
  teclaSoltada(evento: KeyboardEvent): void {
    this.teclas.delete(evento.key.toLowerCase());
  }
  /** Olvida la tecla que se soltó para que el pingüino se detenga. */

  @HostListener('window:blur')
  perderFoco(): void {
    this.teclas.clear();
  }
  /** Si la ventana pierde el foco se sueltan todas las teclas, así el pingüino no queda corriendo. */

  entrar(zona: Zona): void {
    this.router.navigate([zona.ruta]);
  }
  /** Lleva al jugador a la pantalla del edificio. Se usa con Enter o con un clic. */
}
