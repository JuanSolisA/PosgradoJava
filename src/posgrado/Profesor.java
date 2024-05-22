package posgrado;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Profesor extends Docente implements Serializable {

    private ArrayList<Curso> cursos;

    public Profesor(String u, String p, ArrayList<Curso> c) {
        setUsuario(u);
        setPassword(p);
        cursos = c;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public void mostrar() {
        System.out.println("Profesor - Usuario: " + this.getUsuario());
        System.out.println("Password: " + this.getPassword());
        System.out.println("Cursos a cargo: " + cursos.size());
        for (int i = 0; i < cursos.size(); i++) {
            Curso c = cursos.get(i);
            System.out.println(c.getCodigo() + " - " + c.getNombre());
        }
        mostrarOpiniones();
    }

    @Override
    public boolean proceder(SistemaInscripcion sistemaInscripcion) {
        if (!getOpinionesPendientes().isEmpty()) {
            EntradaSalida.mostrarString("Bienvenido/a al sistema, Sr(a) Profesor(a)");
            sistemaInscripcion.consultarDocente(this);
            try {
                sistemaInscripcion.serializar("posgrado.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            EntradaSalida.mostrarString("Bienvenido/a al sistema, Sr(a) Profesor(a)\nNo hay opiniones pendientes en el sistema");
        }
        EntradaSalida.mostrarString("Muchas gracias, Sr(a) Profesor(a). Eso ha sido todo por ahora...");
        return true;
    }
}
