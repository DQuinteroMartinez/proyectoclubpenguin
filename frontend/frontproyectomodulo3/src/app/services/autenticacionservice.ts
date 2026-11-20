import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Loginrequestmodel } from '../models/loginrequestmodel';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Loginresponsemodel } from '../models/loginresponsemodel';
import { Registrorequestmodel } from '../models/registrorequestmodel';
import { Usuariodtomodel } from '../models/usuariodtomodel';


/** Este servicio se comunica con el backend pare registrar cuentas e iniciar sesión,
 * además guarda y consulta el estado de la sesión, tanto el token como el rol */

@Injectable({providedIn: 'root'})
export class Autenticacionservice {

  private readonly apiUrl = environment.apiUrl;
  /** URL base de la API, tomada del archivo de environments */

  private readonly claveToken = 'token';
  /** Clave con la que se guarda el token en localStorage */

  private readonly claveRol = 'rol';
  /** Clave con la que se guarda el rol en localStorage  */

  private readonly claveNombre = 'nombreUsuario'
  /** Clave con la que se guarda el nombre de usuario en localStorage */

  private sesionSubject = new BehaviorSubject<boolean>(false);
  /** Indica si hay una sesión inválida */

  private adminSubject = new BehaviorSubject<boolean>(false);
  /** Indica si el usuario con sesión es Admin*/

  sesionIniciada$ = this.sesionSubject.asObservable();
  /** Si hay una sesión válida emite true, pero false cuando no*/

  esAdmin$ = this.adminSubject.asObservable();
  /** Emite true cuando el usuario con sesion es admin*/

  constructor(private http: HttpClient) {
    const sesionValida = this.haySesionValida();
    this.sesionSubject.next(sesionValida);
    this.adminSubject.next(sesionValida && this.obtenerRol() === 'ADMIN');
  }

  login (datos: Loginrequestmodel): Observable<Loginresponsemodel> {
    return this.http
      .post<Loginresponsemodel>(`${this.apiUrl}/autenticacion/login`, datos)
      .pipe(tap((respuesta) => this.guardarSesion(respuesta)));
  }

  registro (datos: Registrorequestmodel): Observable<Usuariodtomodel> {
    return this.http.post<Usuariodtomodel>(`${this.apiUrl}/usuarios/registro`, datos);
  }

  cerrarSesion(): void{
    localStorage.removeItem(this.claveToken);
    localStorage.removeItem(this.claveRol);
    localStorage.removeItem(this.claveNombre);
    this.sesionSubject.next(false);
    this.adminSubject.next(false);
  }

  obtenerToken(): string | null {
    return localStorage.getItem(this.claveToken);
  }

  obtenerRol(): string | null {
    return localStorage.getItem(this.claveRol);
  }

  obtenerNombreUsuario(): string | null {
    return localStorage.getItem(this.claveNombre);
  }

  haySesionValida(): boolean {
    const token = this.obtenerToken();
    if (!token) {
      return false;
    }
    if (this.tokenVencido(token)) {
      this.cerrarSesion();
      return false;
    }
    return true;
  }

  esAdmin(): boolean{
    return this.haySesionValida() && this.obtenerRol() === 'ADMIN';
  }

  private guardarSesion (respuesta: Loginresponsemodel): void {
    localStorage.setItem(this.claveToken, respuesta.token);
    localStorage.setItem(this.claveRol, respuesta.rol);
    localStorage.setItem(this.claveNombre, respuesta.nombreUsuario);
    this.sesionSubject.next(true);
    this.adminSubject.next(respuesta.rol === 'ADMIN');
  }

  private tokenVencido (token: string): boolean {
    try {
      const cuerpo = token.split('.')[1];
      const texto = atob(cuerpo.replace(/-/g, '+').replace(/_/g, '/'));
      const exp = JSON.parse(texto).exp;
      return Date.now() >= exp * 1000;
    } catch {
      return true;
    }
  }
}
