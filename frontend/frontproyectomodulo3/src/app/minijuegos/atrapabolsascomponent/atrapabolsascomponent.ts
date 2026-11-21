import { Component, HostListener, OnDestroy, OnInit, signal } from '@angular/core';

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
export class Atrapabolsascomponent {

  readonly anchoEscenario = 600;
  readonly altoEscenario = 400;
  readonly anchoCanasta = 70;
  readonly altoCanasta = 40;
  readonly paso = 20;
  readonly tamanoBolsa = 30;
  readonly velocidadCaida = 4;
  readonly intervaloJuego = 30;
  readonly vueltasPorBolsa = 30;
  readonly puntosPorBolsa = 10;
  readonly duracionPartida = 30;
  readonly xInicial = (this.anchoEscenario - this.anchoCanasta) /2;

  x = this.xInicial;
  bolsas = signal<Bolsa[]>([]);
  puntaje = signal(0);
  tiempoRestante = signal(this.duracionPartida);
  terminado = signal(false);

  private contadorVueltas = 0;
  private siguienteId  = 0;
  private temporizador?: ReturnType<typeof setInterval>;
  private temporizadorTiempo?: ReturnType<typeof setInterval>;

  ngOnInit(): void{
    this.iniciarPartida();
  }
  /** Este metodo arranca el bucle del juego al mostrarse el componente */

  ngOnDestroy(): void{
    this.detenerRelojes();
  }
  /** Este metodo detiene el bucle del juego al salir de la pantalla, para que no siga corriendo */

  iniciarPartida(): void{
    this.detenerRelojes();
    this.puntaje.set(0);
    this.bolsas.set([]);
    this.tiempoRestante.set(this.duracionPartida);
    this.terminado.set(false);
    this.contadorVueltas = 0;
    this.x = this.xInicial;
    this.temporizador = setInterval(() => this.actualizarJuego(), this.intervaloJuego);
    this.temporizadorTiempo= setInterval(() => this.restarSegundo, 1000);
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
  }
  /** Permite terminar la partida deteniendo los relojes y muestra el panel de fin */

  private detenerRelojes(): void{
    clearInterval(this.temporizador);
    clearInterval(this.temporizadorTiempo);
  }
  /** Detiene el bucle del juego y el cronometro de segundos */

  private actualizarJuego(): void{

    this.contadorVueltas++;
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
    const solapaEnHorizontal = bolsa.x < this.x + this.anchoCanasta && bolsa.x + this.tamanoBolsa > this.x;

    return llegoALaAltura && solapaEnHorizontal;
  }
  /** Este metodo dice si una bolsa esta tocando la canasta. Indica que hay choque cuando la bolsa
   * llega a la alruta de la canasta y se solapan de forma horizontal*/

  @HostListener('window:keydown', ['$event'])
  moverCanasta(evento: KeyboardEvent) {

    const maximoX = this.anchoEscenario - this.anchoCanasta;

    switch(evento.key){

      case 'ArrowLeft':
        this.x = Math.max(0, this.x - this.paso);
        break;

      case 'ArrowRight':
        this.x = Math.max(maximoX, this.x + this.paso);
        break;

      default:
        return;
    }
    evento.preventDefault();
  }
  /** Ayuda a mover la canasta de izquierda a derecha cuando se pulsa una flecha del teclado.
   * También se limita la posicion para que la canasta no salga del area limitada */
}
