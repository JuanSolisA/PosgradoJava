package posgrado;

import java.io.Serializable;
import java.util.ArrayList;

public class CurriculaAlumno implements Serializable {

    private ArrayList<Curso> cursosInscriptos;

    public CurriculaAlumno() {
        cursosInscriptos = new ArrayList<Curso>();
    }

    public ArrayList<Curso> consultarCurricula() {
        return cursosInscriptos;
    }
}
