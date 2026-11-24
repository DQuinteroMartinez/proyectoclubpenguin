export interface Loginresponsemodel {

  rol: 'ADMIN' | 'USUARIO';
  /**Los unicos roles aceptados: administrador (ADMIN) o usuario (USUARIO)*/

  token: string;
  /** Token JWT que se enviará en el encabezado 'Authorization' de cada petición protegida*/

  nombreUsuario: string;
  /** Nombre de usuario ya autenticado */
}
