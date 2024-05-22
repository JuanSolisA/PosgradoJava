package posgrado;

import java.io.IOException;

public class Control {

    public void ejecutar() {

        SistemaInscripcion sistemaInscripcion = new SistemaInscripcion();

        boolean seguir;
        try {
            sistemaInscripcion = sistemaInscripcion.deSerializar("posgrado.txt");
            seguir = EntradaSalida.leerBoolean("SISTEMA DE POSGRADO\nDesea ingresar?");
        } catch (Exception e) {
            String usuario = EntradaSalida.leerString("Arranque inicial del sistema.\n"
                    + "Sr(a) Coordinador(a), ingrese su nombre de usuario:");
            if (usuario.equals("")) {
                throw new NullPointerException("ERROR: El usuario no puede ser nulo.");
            }
            String password = EntradaSalida.leerPassword("Ingrese su password:");
            if (password.equals("")) {
                throw new NullPointerException("ERROR: La password no puede ser nula.");
            }
            sistemaInscripcion.getPersonas().add(new Coordinador(usuario, password));
            try {
                sistemaInscripcion.serializar("posgrado.txt");
                EntradaSalida.mostrarString("El arranque ha sido exitoso. Ahora se debe reiniciar el sistema...");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            seguir = false;
        }

        while (seguir) {
            String usuario = EntradaSalida.leerString("Ingrese el usuario:");
            String password = EntradaSalida.leerPassword("Ingrese la password:");

            Persona p = sistemaInscripcion.buscarPersona(usuario + ":" + password);

            if (p == null) {
                EntradaSalida.mostrarString("ERROR: La combinacion usuario/password ingresada no es valida.");
            } else {
                seguir = p.proceder(sistemaInscripcion);  // POLIMORFISMO!!!!
            }
        }
    }
}
