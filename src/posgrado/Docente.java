package posgrado;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Docente extends Persona implements Serializable {

    private ArrayList<Opinion> opinionesPendientes;
    private ArrayList<Opinion> opinionesFavorables;
    private ArrayList<Opinion> opinionesContrarias;

    public Docente() {
        opinionesPendientes = new ArrayList<Opinion>();
        opinionesFavorables = new ArrayList<Opinion>();
        opinionesContrarias = new ArrayList<Opinion>();
    }

    public ArrayList<Opinion> getOpinionesContrarias() {
        return opinionesContrarias;
    }

    public void setOpinionesContrarias(ArrayList<Opinion> opinionesContrarias) {
        this.opinionesContrarias = opinionesContrarias;
    }

    public ArrayList<Opinion> getOpinionesFavorables() {
        return opinionesFavorables;
    }

    public void setOpinionesFavorables(ArrayList<Opinion> opinionesFavorables) {
        this.opinionesFavorables = opinionesFavorables;
    }

    public ArrayList<Opinion> getOpinionesPendientes() {
        return opinionesPendientes;
    }

    public void setOpinionesPendientes(ArrayList<Opinion> opinionesPendientes) {
        this.opinionesPendientes = opinionesPendientes;
    }

    public void consultarOpinion(Curso curso, Aspirante aspi) {
        CurriculaAlumno cA = aspi.getCurriculaAlumno();
        ArrayList<Curso> cursosHechos = cA.consultarCurricula();
        Opinion op = new Opinion(curso, cursosHechos);
        this.getOpinionesPendientes().add(op);
    }

    public void mostrarOpiniones() {
        listarOpiniones(opinionesPendientes, "Opiniones pendientes: ");
        listarOpiniones(opinionesFavorables, "Opiniones favorables: ");
        listarOpiniones(opinionesContrarias, "Opiniones contrarias: ");
        System.out.println("--------------------");
    }

    private void listarOpiniones(ArrayList<Opinion> opi, String titulo) {
        System.out.println(titulo + opi.size());
        for (int i = 0; i < opi.size(); i++) {
            Opinion o = opi.get(i);
            Curso c = o.getCursoNuevo();
            ArrayList<Curso> curHechos = o.getCursosHechos();

            String cartel = "Autorizar " + c.getCodigo() + " - " + c.getNombre();
            if (curHechos.isEmpty()) {
                cartel = cartel + ", sin haber hecho ningun curso anteriormente?";
            } else {
                cartel = cartel + "? Los cursos hechos anteriormente son: ";
            }
            for (int j = 0; j < curHechos.size(); j++) {
                cartel = cartel + curHechos.get(j).getCodigo() + " - "
                        + curHechos.get(j).getNombre() + "   ";
            }
            System.out.println(cartel);
        }

    }
}
