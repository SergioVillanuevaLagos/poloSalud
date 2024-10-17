package modelos;

import java.util.Date;

public class comentario {
    private int IDcoment;
    private int IDpubli;
    private String comentario;
    private int IDusuario;
    private Date fechCreacion;

    public comentario(int IDcoment, int IDpubli, String comentario, int IDusuario, Date fechCreacion) {
        this.IDcoment = IDcoment;
        this.IDpubli = IDpubli;
        this.comentario = comentario;
        this.IDusuario = IDusuario;
        this.fechCreacion = fechCreacion;
    }

    public int getIDcoment() {
        return IDcoment;
    }

    public void setIDcoment(int IDcoment) {
        this.IDcoment = IDcoment;
    }

    public int getIDpubli() {
        return IDpubli;
    }

    public void setIDpubli(int IDpubli) {
        this.IDpubli = IDpubli;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIDusuario() {
        return IDusuario;
    }

    public void setIDusuario(int IDusuario) {
        this.IDusuario = IDusuario;
    }

    public Date getFechCreacion() {
        return fechCreacion;
    }

    public void setFechCreacion(Date fechCreacion) {
        this.fechCreacion = fechCreacion;
    }
}
