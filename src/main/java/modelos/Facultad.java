package modelos;

public class Facultad {
    private int IDdept;
    private String nombre;
    private String descripcion;
    private int IDfacultad;

    public Facultad(int IDdept, String nombre, String descripcion, int IDfacultad) {
        this.IDdept = IDdept;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.IDfacultad = IDfacultad;
    }

    public int getIDdept() {
        return IDdept;
    }

    public void setIDdept(int IDdept) {
        this.IDdept = IDdept;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIDfacultad() {
        return IDfacultad;
    }

    public void setIDfacultad(int IDfacultad) {
        this.IDfacultad = IDfacultad;
    }
}
