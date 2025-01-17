package posgrado;

import java.io.Serializable;

public abstract class Persona implements Serializable {

    private String usuario;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public abstract boolean proceder(SistemaInscripcion sistemaInscripcion);

    public abstract void mostrar();
}
