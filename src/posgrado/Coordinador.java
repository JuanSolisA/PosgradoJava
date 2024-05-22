package posgrado;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Coordinador extends Docente implements Serializable {

    public Coordinador(String u, String p) {
        setUsuario(u);
        setPassword(p);
    }

    @Override
    public void mostrar() {
        System.out.println("Coordinador - Usuario: " + this.getUsuario());
        System.out.println("Password: " + this.getPassword());
        mostrarOpiniones();
    }

    @Override
    public boolean proceder(SistemaInscripcion sistemaInscripcion) {
        char opc;
        boolean seguir = true;
        do {
            opc = EntradaSalida.leerChar(
                    "OPCIONES DEL COORDINADOR\n"
                    + "[1] Dar de alta un curso\n"
                    + "[2] Dar de alta un profesor\n"
                    + "[3] Dar de alta un aspirante\n"
                    + "[4] Ingresar opiniones\n"
                    + "[5] Mostrar el contenido del sistema\n"
                    + "[6] Salir de este menu\n"
                    + "[7] Salir del sistema");
            switch (opc) {
                case '1':
                    String nombre = EntradaSalida.leerString("ALTA DE CURSO\nNombre del curso:");
                    if (nombre.equals("")) {
                        EntradaSalida.mostrarString("ERROR: nombre no valido");
                    } else {
                        ArrayList<Curso> ListadoCursos = sistemaInscripcion.getCursos();
                        ArrayList<String> ListadoCodigos = new ArrayList<String>();
                        for (int i = 0; i < ListadoCursos.size(); i++) {
                            ListadoCodigos.add(ListadoCursos.get(i).getCodigo());
                        }
                        String codigo = EntradaSalida.leerString("Codigo del curso:");
                        if (codigo.equals("")) {
                            EntradaSalida.mostrarString("ERROR: codigo no valido");
                        } else {
                            if (ListadoCodigos.contains(codigo)) {
                                EntradaSalida.mostrarString("ERROR: El codigo ya figura en el sistema");
                            } else {
                                ArrayList<String> correlativas = new ArrayList<String>();
                                String correl;
                                Curso cur;
                                boolean masCorrel = EntradaSalida.leerBoolean("Desea ingresar correlativas?");
                                while (masCorrel) {
                                    String cursosStr = "";
                                    for (int i = 0; i < ListadoCursos.size(); i++) {
                                        cur = ListadoCursos.get(i);
                                        if (!correlativas.contains(cur.getCodigo())) {
                                            cursosStr = cursosStr + cur.getCodigo() + " - " + cur.getNombre() + '\n';
                                        }
                                    }
                                    if (cursosStr.equals("")) {
                                        masCorrel = false;
                                        EntradaSalida.mostrarString("No hay mas cursos disponibles en el sistema");
                                    } else {
                                        correl = EntradaSalida.leerString("Correlativas posibles:\n" + cursosStr + "Ingrese uno de los codigos listados arriba: ");
                                        if (correl.equals("") || !ListadoCodigos.contains(correl) || correlativas.contains(correl)) {
                                            EntradaSalida.mostrarString("ERROR: codigo no valido");
                                        } else {
                                            correlativas.add(correl);
                                        }
                                        masCorrel = EntradaSalida.leerBoolean("Desea ingresar mas correlativas?");
                                    }
                                }
                                Curso curso = new Curso();
                                curso.setNombre(nombre);
                                curso.setCodigo(codigo);
                                curso.setCorrelativas(correlativas);
                                sistemaInscripcion.getCursos().add(curso);
                                EntradaSalida.mostrarString("Se ha incorporado el curso al sistema");
                            }
                        }
                    }
                    break;
                case '2':
                    ArrayList<Curso> ListadoCursos = sistemaInscripcion.getCursos();
                    if (ListadoCursos.isEmpty()) {
                        EntradaSalida.mostrarString("ERROR: Primero deben darse de alta los cursos");
                    } else {
                        String usProf = EntradaSalida.leerString("ALTA DE PROFESOR\nIngrese el usuario:");
                        if (usProf.equals("")) {
                            EntradaSalida.mostrarString("ERROR: usuario no valido");
                        } else {
                            String paProf = EntradaSalida.leerPassword("Ingrese la password:");
                            if (paProf.equals("")) {
                                EntradaSalida.mostrarString("ERROR: password no valida");
                            } else {
                                Persona p = sistemaInscripcion.buscarPersona(usProf + ":" + paProf);
                                if (p != null) {
                                    EntradaSalida.mostrarString("ERROR: El usuario ya figura en el sistema");
                                } else {
                                    ArrayList<Curso> curProf = new ArrayList<Curso>();
                                    Curso cur = null;
                                    String codCurso;
                                    boolean opcValida;
                                    boolean masCursos;
                                    do {
                                        String cursosStr = "";
                                        for (int i = 0; i < ListadoCursos.size(); i++) {
                                            cur = ListadoCursos.get(i);
                                            if (!curProf.contains(cur)) {
                                                cursosStr = cursosStr + cur.getCodigo() + " - " + cur.getNombre() + '\n';
                                            }
                                        }
                                        if (cursosStr.equals("")) {
                                            masCursos = false;
                                            EntradaSalida.mostrarString("No hay mas cursos disponibles en el sistema");
                                        } else {
                                            codCurso = EntradaSalida.leerString(cursosStr + "Elija un curso, ingresando el codigo: ");
                                            int i = 0;
                                            opcValida = false;
                                            while (i < ListadoCursos.size() && !opcValida) {
                                                cur = ListadoCursos.get(i);
                                                if (cur.getCodigo().equals(codCurso)) {
                                                    opcValida = true;
                                                } else {
                                                    i++;
                                                }
                                            }
                                            if (!opcValida || curProf.contains(cur)) {
                                                EntradaSalida.mostrarString("ERROR: codigo no valido");
                                            } else {
                                                curProf.add(cur);
                                            }
                                            masCursos = EntradaSalida.leerBoolean("Desea ingresar mas cursos?");
                                        }
                                    } while (masCursos);

                                    if (curProf.isEmpty()) {
                                        EntradaSalida.mostrarString("ERROR: No se ha ingresado ningun curso");
                                    } else {
                                        p = new Profesor(usProf, paProf, curProf);
                                        sistemaInscripcion.getPersonas().add(p);
                                        EntradaSalida.mostrarString("Se ha incorporado el profesor al sistema");
                                    }
                                }
                            }
                        }
                    }
                    break;
                case '3':
                    String usAspi = EntradaSalida.leerString("ALTA DE ASPIRANTE\nIngrese el usuario:");
                    if (usAspi.equals("")) {
                        EntradaSalida.mostrarString("ERROR: usuario no valido");
                    } else {
                        String paAspi = EntradaSalida.leerPassword("Ingrese la password:");
                        if (paAspi.equals("")) {
                            EntradaSalida.mostrarString("ERROR: password no valida");
                        } else {
                            Persona p = sistemaInscripcion.buscarPersona(usAspi + ":" + paAspi);
                            if (p != null) {
                                EntradaSalida.mostrarString("ERROR: El usuario ya figura en el sistema");
                            } else {
                                p = new Aspirante(usAspi, paAspi);
                                sistemaInscripcion.getPersonas().add(p);
                                EntradaSalida.mostrarString("Se ha incorporado el aspirante al sistema");
                            }
                        }
                    }
                    break;
                case '4':
                    if (!getOpinionesPendientes().isEmpty()) {
                        sistemaInscripcion.consultarDocente(this);
                    } else {
                        EntradaSalida.mostrarString("No hay opiniones pendientes en el sistema");
                    }
                    break;
                case '5':
                    System.out.println("\n=============================================");
                    System.out.println("Personas");
                    ArrayList<Persona> vecPer = sistemaInscripcion.getPersonas();
                    for (int i = 0; i < vecPer.size(); i++) {
                        vecPer.get(i).mostrar();
                    }
                    System.out.println("\nCursos");
                    ArrayList<Curso> vecCur = sistemaInscripcion.getCursos();
                    if (vecCur.isEmpty()) {
                        System.out.println("No hay cursos registrados en el sistema.");
                    } else {
                        for (int i = 0; i < vecCur.size(); i++) {
                            vecCur.get(i).mostrar();
                        }
                    }
                    break;
                case '6':
                    seguir = true;
                    break;
                case '7':
                    seguir = false;
                    break;
                default:
                    EntradaSalida.mostrarString("ERROR: Opcion invalida");
                    opc = '*';
            }
            if (opc >= '1' && opc <= '4') {
                try {
                    sistemaInscripcion.serializar("posgrado.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } while (opc != '6' && opc != '7');

        return seguir;

    }
}
