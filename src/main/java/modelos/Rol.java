package modelos;

public class Rol {
    private int IDrol;
    private String nombreRol;

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getIDrol() {
        return IDrol;
    }

    public void setIDrol(int IDrol) {
        this.IDrol = IDrol;
    }

    public Rol(int IDrol, String nombreRol) {
        this.IDrol = IDrol;
        this.nombreRol = nombreRol;
    }
}
