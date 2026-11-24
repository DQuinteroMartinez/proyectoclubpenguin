export interface Usuariodtomodel {

  rol: 'ADMIN' | 'USUARIO';
  /**Los unicos roles aceptados: administrador (ADMIN) o usuario (USUARIO)*/

  id: number;
  /** Identificador único del usuario */

  nombreUsuario: string;
  /** Nombre de usuario */

  correo?: string;
  /** este es un valor opcional porque el backend podría dejar de enviarlo */

  fechaRegistro: string;
  /** Fecha de registro en texto ISO-8601 sin la zona horaria */

}
