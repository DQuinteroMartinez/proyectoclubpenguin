export interface Registrorequestmodel {

  nombreUsuario: string;
  /** Nombre de usuario, válido entre 3 a 20 caracteres*/

  correo: string;
  /** Correo electrónico con formato válido*/

  password: string;
  /** Contraseña de mínimo 6 caracteres */
  
}
