package posgrado;

public class Main {

    public static void main(String[] args) {
        Control c = new Control();
        try {
            c.ejecutar();
        } catch (NullPointerException e) {
            EntradaSalida.mostrarString(e.getMessage());
        }
    }
}
