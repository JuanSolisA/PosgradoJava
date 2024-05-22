package posgrado;

import java.io.Serializable;
import java.util.ArrayList;

public class Opinion implements Serializable {

    private Curso cursoNuevo;
    private ArrayList<Curso> cursosHechos;

    public Opinion(Curso cursoNuevo, ArrayList<Curso> cursosHechos) {
        this.cursoNuevo = cursoNuevo;
        this.cursosHechos = new ArrayList<Curso>(cursosHechos);
    }

    public Curso getCursoNuevo() {
        return cursoNuevo;
    }

    public void setCursoNuevo(Curso cursoNuevo) {
        this.cursoNuevo = cursoNuevo;
    }

    public ArrayList<Curso> getCursosHechos() {
        return cursosHechos;
    }

    public void setCursosHechos(ArrayList<Curso> cursosHechos) {
        this.cursosHechos = cursosHechos;
    }
}
