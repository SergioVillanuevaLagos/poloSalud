package com.example.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Base64;

@Entity
@Table(name = "publicacion")
public class publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_publi")
    private Integer idPublicacion;

    @Column(name = "categoria", length = 500)
    private String categoria;

    @Column(name = "arch_adj", columnDefinition = "LONGBLOB")
    private byte[] archivoAdjunto;
    

    @Column(name = "URL_publi", length = 500)
    private String urlPublicacion;

    @Column(name = "fech_publi")
    @Temporal(TemporalType.DATE)
    private Date fechPublicacion;

    @Column(name = "titulo", length = 500)
    private String titulo;

    @Column(name = "subtitulo", length = 500)
    private String subtitulo;

    @Column(name = "contenido", columnDefinition = "MEDIUMTEXT")
    private String contenido;

    @Column(name = "ID_admin")
    private Integer idAdmin;

    // Getters y Setters

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

    public byte[] getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(byte[] archivoAdjunto) {
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

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
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
    public String getImagenUrl() {
        if (archivoAdjunto != null) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(archivoAdjunto);
        }
        return null;
    }
}