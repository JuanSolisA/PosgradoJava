package posgrado;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SistemaInscripcion implements Serializable {

    private ArrayList<Curso> cursos;
    private ArrayList<Persona> personas;

    public SistemaInscripcion() {
        cursos = new ArrayList<Curso>();
        personas = new ArrayList<Persona>();
    }

    public SistemaInscripcion deSerializar(String a) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(a);
        ObjectInputStream o = new ObjectInputStream(f);
        SistemaInscripcion s = (SistemaInscripcion) o.readObject();
        o.close();
        f.close();
        return s;
    }

    public void serializar(String a) throws IOException {
        FileOutputStream f = new FileOutputStream(a);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(this);
        o.close();
        f.close();
    }

    public void pantallaInscripcion() {
        EntradaSalida.mostrarString("Bienvenido/a al sistema de eleccion de cursos, Sr(a) Aspirante");
    }

    public Persona buscarPersona(String datos) {
        int i = 0;
        boolean encontrado = false;
        Persona p = null;

        while (i < personas.size() && !encontrado) {
            p = personas.get(i);
            if (datos.equals(p.getUsuario() + ":" + p.getPassword())) {
                encontrado = true;
            } else {
                i++;
            }
        }
        if (!encontrado) {
            return null;
        } else {
            return p;
        }
    }

    public void elegirCurso(Aspirante aspi) {
        String cursosStr = "";
        String codCurso;
        Curso curso = null;
        boolean opcValida = false;
        for (int i = 0; i < cursos.size(); i++) {
            curso = cursos.get(i);
            cursosStr = cursosStr + curso.getCodigo() + " - " + curso.getNombre() + '\n';
        }
        codCurso = EntradaSalida.leerString(cursosStr + "Elija un curso, ingresando el codigo: ");
        int j = 0;
        while (j < cursos.size() && !opcValida) {
            curso = cursos.get(j);
            if (curso.getCodigo().equals(codCurso)) {
                opcValida = true;
            } else {
                j++;
            }
        }
        if (opcValida) {
            if (curso.chequearCorrelativas(aspi.getCurriculaAlumno())) {
                aspi.getCursos().add(curso);
                EntradaSalida.mostrarString("Su solicitud va a ser evaluada por el profesor del curso y el coordinador");
                Persona p = null;
                int i = 0;
                boolean encontrado = false;

                Profesor profe = null;
                while (i < personas.size() && !encontrado) {
                    p = personas.get(i);
                    if (p instanceof Profesor) {
                        profe = (Profesor) p;
                        if (profe.getCursos().contains(curso)) {
                            encontrado = true;
                        } else {
                            i++;
                        }
                    } else {
                        i++;
                    }
                }

                profe.consultarOpinion(curso, aspi);

                i = 0;
                encontrado = false;
                while (i < personas.size() && !encontrado) {
                    p = personas.get(i);
                    if (p instanceof Coordinador) {
                        encontrado = true;
                    } else {
                        i++;
                    }
                }
                Coordinador coor = (Coordinador) p;
                coor.consultarOpinion(curso, aspi);
            } else {
                EntradaSalida.mostrarString("ERROR: Correlativas insuficientes");
            }
        } else {
            EntradaSalida.mostrarString("ERROR: Opcion no valida");
        }
    }

    public void consultarDocente(Docente docente) {
        boolean masOpiniones = EntradaSalida.leerBoolean("Desea ingresar opiniones?");
        Curso curNuevo;
        ArrayList<Curso> curHechos;
        String cartel;
        Opinion opinion;
        while (masOpiniones && !docente.getOpinionesPendientes().isEmpty()) {

            opinion = docente.getOpinionesPendientes().get(0);
            curNuevo = opinion.getCursoNuevo();
            curHechos = opinion.getCursosHechos();

            cartel = "Autorizar " + curNuevo.getCodigo() + " - " + curNuevo.getNombre();

            if (curHechos.isEmpty()) {
                cartel = cartel + "\nsin haber hecho ningun curso anteriormente?\n";
            } else {
                cartel = cartel + "?\nLos cursos hechos anteriormente son:\n";
            }
            for (int i = 0; i < curHechos.size(); i++) {
                cartel = cartel + curHechos.get(i).getCodigo() + " - "
                        + curHechos.get(i).getNombre() + '\n';
            }

            boolean opinionFavorable = EntradaSalida.leerBoolean(cartel);
            if (opinionFavorable) {
                docente.getOpinionesPendientes().remove(opinion);
                docente.getOpinionesFavorables().add(opinion);
            } else {
                docente.getOpinionesPendientes().remove(opinion);
                docente.getOpinionesContrarias().add(opinion);
            }
            masOpiniones = EntradaSalida.leerBoolean("Desea ingresar mas opiniones?");
        }
        if (docente.getOpinionesPendientes().isEmpty()) {
            EntradaSalida.mostrarString("No hay mas opiniones pendientes en el sistema");
        }
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<Persona> personas) {
        this.personas = personas;
    }
}
