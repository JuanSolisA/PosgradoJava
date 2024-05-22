package posgrado;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Aspirante extends Persona implements Serializable {

    private ArrayList<Curso> cursos;
    private CurriculaAlumno curriculaAlumno;

    public Aspirante(String u, String p) {
        setUsuario(u);
        setPassword(p);
        cursos = new ArrayList<Curso>();
        curriculaAlumno = new CurriculaAlumno();
    }

    public CurriculaAlumno getCurriculaAlumno() {
        return curriculaAlumno;
    }

    public void setCurriculaAlumno(CurriculaAlumno curriculaAlumno) {
        this.curriculaAlumno = curriculaAlumno;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    public void notificarAlumno(Curso c, boolean aceptado) {
        if (aceptado) {
            EntradaSalida.mostrarString("Ud. ha sido inscripto en el curso:\n"
                    + c.getCodigo() + " - " + c.getNombre());
        } else {
            EntradaSalida.mostrarString("Ud. ha sido rechazado en el curso:\n"
                    + c.getCodigo() + " - " + c.getNombre());
        }
    }

    @Override
    public void mostrar() {
        System.out.println("Aspirante - Usuario: " + this.getUsuario());
        System.out.println("Password: " + this.getPassword());
        System.out.println("Cursos pendientes: " + cursos.size());
        for (int i = 0; i < cursos.size(); i++) {
            Curso c = cursos.get(i);
            System.out.println(c.getCodigo() + " - " + c.getNombre());
        }
        ArrayList<Curso> cursosCurr = curriculaAlumno.consultarCurricula();
        System.out.println("Cursos en su curricula: " + cursosCurr.size());
        for (int i = 0; i < cursosCurr.size(); i++) {
            Curso c = cursosCurr.get(i);
            System.out.println(c.getCodigo() + " - " + c.getNombre());
        }
        System.out.println("--------------------");
    }

    @Override
    public boolean proceder(SistemaInscripcion sistemaInscripcion) {
        Curso curso;
        ArrayList<Persona> personas = sistemaInscripcion.getPersonas();
        Persona persona;
        int cantOpinionesFavorables;
        int cantOpinionesContrarias;
        for (int i = 0; i < cursos.size(); i++) {
            curso = cursos.get(i);
            cantOpinionesFavorables = 0;
            cantOpinionesContrarias = 0;
            for (int j = 0; j < personas.size(); j++) {
                persona = personas.get(j);
                ArrayList<Opinion> opinionesFav;
                Opinion opinionFav;
                ArrayList<Opinion> opinionesCon;
                Opinion opinionCon;

                if (persona instanceof Docente) {
                    Docente doc = (Docente) persona;
                    opinionesFav = doc.getOpinionesFavorables();
                    opinionesCon = doc.getOpinionesContrarias();
                    for (int k = 0; k < opinionesFav.size(); k++) {
                        opinionFav = opinionesFav.get(k);
                        if (curso.getCodigo().equals(opinionFav.getCursoNuevo().getCodigo())
                                && getCurriculaAlumno().consultarCurricula().containsAll(opinionFav.getCursosHechos())) {
                            cantOpinionesFavorables++;
                        }
                    }
                    for (int k = 0; k < opinionesCon.size(); k++) {
                        opinionCon = opinionesCon.get(k);
                        if (curso.getCodigo().equals(opinionCon.getCursoNuevo().getCodigo())
                                && getCurriculaAlumno().consultarCurricula().containsAll(opinionCon.getCursosHechos())) {
                            cantOpinionesContrarias++;
                        }
                    }
                }
            }

            if (cantOpinionesContrarias > 0) {
                curso.rechazarAlumno(this);
                notificarAlumno(curso, false);
            } else {
                if (cantOpinionesFavorables > 1) {
                    curso.inscribirAlumno(this);
                    notificarAlumno(curso, true);
                }
            }
        }
        if (sistemaInscripcion.getCursos().isEmpty()) {
            EntradaSalida.mostrarString("ERROR: Aun no hay ningun curso cargado en el sistema");
        } else {
            boolean opcInscribir = EntradaSalida.leerBoolean("Sr(a) Aspirante, desea Ud. ingresar al sistema de eleccion de cursos?");
            if (opcInscribir) {
                sistemaInscripcion.pantallaInscripcion();
                sistemaInscripcion.elegirCurso(this);
            }
        }
        EntradaSalida.mostrarString("Muchas gracias, Sr(a) Aspirante. Eso ha sido todo por ahora...");
        try {
            sistemaInscripcion.serializar("posgrado.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
