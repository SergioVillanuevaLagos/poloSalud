package modelos;

public class colaboradores {
private String nombre;
private String URL;
private String descripcion;
private int IDcolab;

    public colaboradores(String nombre, String URL, String descripcion, int IDcolab) {
        this.nombre = nombre;
        this.URL = URL;
        this.descripcion = descripcion;
        this.IDcolab = IDcolab;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIDcolab() {
        return IDcolab;
    }

    public void setIDcolab(int IDcolab) {
        this.IDcolab = IDcolab;
    }
}
