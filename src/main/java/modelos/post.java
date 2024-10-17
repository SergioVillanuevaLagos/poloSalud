package modelos;

import java.util.Date;

public class post {
     private int id;
     private String descripcion;
     private String urlfot;
     private Date fecha;
     private String titulo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlfot() {
        return urlfot;
    }

    public void setUrlfot(String urlfot) {
        this.urlfot = urlfot;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public post(int id, String descripcion, String urlfot, Date fecha, String titulo) {
        this.id = id;
        this.descripcion = descripcion;
        this.urlfot = urlfot;
        this.fecha = fecha;
        this.titulo = titulo;
    }

    public post() {


    }
}
