import { Component, OnInit, signal } from '@angular/core';
import { Minijuegodtomodel } from '../../models/minijuegodtomodel';
import { Rankingdtomodel }  from '../../models/rankingdtomodel';
import { Minijuegoservice } from '../../services/minijuegoservice';
import { Rankingservice } from '../../services/rankingservice';
import { Reporteservice } from '../../services/reporteservice';
import { HttpErrorResponse } from '@angular/common/http';

/** Pantalla de ranking que permite elegir un minijuego, ver su Top 10 y descargar en ranking en
 * un PDF */

@Component({
  selector: 'app-rankingcomponent',
  standalone: false,
  styleUrl: './rankingcomponent.css',
  templateUrl: './rankingcomponent.html',
})
export class Rankingcomponent implements OnInit {

  minijuegos = signal<Minijuegodtomodel[]>([]);
  minijuegoElegido = signal<number | null>(null);
  ranking = signal<Rankingdtomodel[]>([]);

  cargandoMinijuegos = signal(true);
  cargandoRanking = signal(false);
  descargando = signal(false);

  mensajeErrorMinijuegos = signal('');
  mensajeErrorRanking = signal('');
  mensajeErrorPdf = signal('');

  constructor (private minijuegoservice: Minijuegoservice, private rankingservice: Rankingservice,
  private reporteservice: Reporteservice){

  }

  ngOnInit() {
    this.minijuegoservice.listar().subscribe({
      next: (lista) => {
        this.minijuegos.set(lista);
        this.cargandoMinijuegos.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoMinijuegos.set(false);
        this.mensajeErrorMinijuegos.set(
          this.mensajeDe(error, 'No se pudo cargar la lista de minijuegos')
        );
      },
    });
  }
  /** Se ejecuta al mostrarse el componente, cargando los minijuegos del selector */

  seleccionar(valor: string): void{

    this.mensajeErrorRanking.set('');
    this.mensajeErrorPdf.set('');
    this.ranking.set([]);

    const id = Number(valor);
    if(!id){
      this.minijuegoElegido.set(null);
      return;
    }
    this.minijuegoElegido.set(id);
    this.cargarRanking(id);
  }
  /** ¨Se ejecuta cuando un usuario elige un minijuego del selector */

  private cargarRanking (id: number): void {
    this.cargandoRanking.set(true);
    this.rankingservice.porMinijuego(id).subscribe({
      next: (filas) => {
        this.ranking.set(filas);
        this.cargandoRanking.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.cargandoRanking.set(false);
        this.mensajeErrorRanking.set(this.mensajeDe(error, 'No se pudo cargar el ranking'));
      },
    });
  }
  /** Pide al backend el Top 10 de un minijuego */

  descargarPdf(): void{
    const id = this.minijuegoElegido();
    if (id === null) {
      return;
    }

    this.descargando.set(true);
    this.mensajeErrorPdf.set('');
    this.reporteservice.descargarRanking(id).subscribe({
      next: () => this.descargando.set(false), error: (error: HttpErrorResponse) => {
        this.descargando.set(false);
        this.interpretarErrorPdf(error);
      },
    });
  }
  /** *Se descarga el ranking del minijuego en formato PDF */

  private interpretarErrorPdf (error: HttpErrorResponse) {
    const mensajePorDefecto = 'No se pudo descargar el PDF';

    if (error.status === 0) {
      this.mensajeErrorPdf.set('No se pudo conectar con el servidor');
      return;
    }

    if (error.error instanceof Blob){
      error.error.text().then((texto) =>{
        try{
          this.mensajeErrorPdf.set(JSON.parse(texto).error ?? mensajePorDefecto);
        } catch {
          this.mensajeErrorPdf.set(mensajePorDefecto);
        }
      });
      return;
    }
    this.mensajeErrorPdf.set(mensajePorDefecto);
  }
/** Convierte el error de la pantalla del PDF en un mensaje para el usuario */

  private mensajeDe(error: HttpErrorResponse, mensajePorDefecto: string): string {
    if (error.status === 0){
      return 'No se pudo conectar con el servidor';
    }
    return error.error?.error ?? mensajePorDefecto;
  }
/** Obtiene el mensaje de error que se le muestra al usuario */
}

