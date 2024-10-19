package com.example.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "publicacion")
public class publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_publi")
    private Integer idPublicacion;

    @Column(name = "categoria", length = 500)
    private String categoria;

    @Column(name = "arch_adj", columnDefinition = "TEXT")
    private String archivoAdjunto;

    @Column(name = "URL_publi", length = 500)
    private String urlPublicacion;

    @Column(name = "fech_publi")
    @Temporal(TemporalType.DATE)
    private Date fechPublicacion;

    @Column(name = "titulo", length = 500)
    private String titulo;

    @Column(name = "contenido", columnDefinition = "MEDIUMTEXT")
    private String contenido;

    @Column(name = "ID_admin")
    private Integer idAdmin;

    public Integer getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(Integer idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(String archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    public String getUrlPublicacion() {
        return urlPublicacion;
    }

    public void setUrlPublicacion(String urlPublicacion) {
        this.urlPublicacion = urlPublicacion;
    }

    public Date getFechPublicacion() {
        return fechPublicacion;
    }

    public void setFechPublicacion(Date fechPublicacion) {
        this.fechPublicacion = fechPublicacion;
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

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }
}
