package modelos;

import java.util.Date;

public class publicacion {
     private int IDpubli;
     private String categoria;
     private String urlpubli;
     private Date fechaPubli;
     private String titulo;
     private String contenido;
     private int IDAdmin;
     private String archioc;

    public int getIDpubli() {
        return IDpubli;
    }

    public void setIDpubli(int IDpubli) {
        this.IDpubli = IDpubli;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUrlpubli() {
        return urlpubli;
    }

    public void setUrlpubli(String urlpubli) {
        this.urlpubli = urlpubli;
    }

    public Date getFechaPubli() {
        return fechaPubli;
    }

    public void setFechaPubli(Date fechaPubli) {
        this.fechaPubli = fechaPubli;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getIDAdmin() {
        return IDAdmin;
    }

    public void setIDAdmin(int IDAdmin) {
        this.IDAdmin = IDAdmin;
    }

    public String getArchioc() {
        return archioc;
    }

    public void setArchioc(String archioc) {
        this.archioc = archioc;
    }

    public publicacion(int IDpubli, String urlpubli, String categoria, String contenido, Date fechaPubli, String titulo, String archioc, int IDAdmin) {
        this.IDpubli = IDpubli;
        this.urlpubli = urlpubli;
        this.categoria = categoria;
        this.contenido = contenido;
        this.fechaPubli = fechaPubli;
        this.titulo = titulo;
        this.archioc = archioc;
        this.IDAdmin = IDAdmin;
    }
}
