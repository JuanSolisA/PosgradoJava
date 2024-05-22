package posgrado;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {

    private String nombre;
    private String codigo;
    private ArrayList<String> correlativas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ArrayList<String> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(ArrayList<String> correlativas) {
        this.correlativas = correlativas;
    }

    public boolean chequearCorrelativas(CurriculaAlumno cA) {
        ArrayList<String> vS = new ArrayList<String>();
        ArrayList<Curso> vC = cA.consultarCurricula();
        for (int i = 0; i < vC.size(); i++) {
            vS.add(vC.get(i).getCodigo());
        }
        return vS.containsAll(correlativas);
    }

    public void inscribirAlumno(Aspirante aspi) {
        aspi.getCurriculaAlumno().consultarCurricula().add(this);
        aspi.getCursos().remove(this);
    }

    public void rechazarAlumno(Aspirante aspi) {
        aspi.getCursos().remove(this);
    }

    public void mostrar() {
        System.out.println("Curso - Codigo: " + codigo);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correlativas: " + correlativas.size());
        for (int i = 0; i < correlativas.size(); i++) {
            System.out.println(correlativas.get(i));
        }
        System.out.println("********************");
    }
}
