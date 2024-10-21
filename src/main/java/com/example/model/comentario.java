package com.example.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comentario")
public class comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_comen")
    private int idComen;

    @Column(name = "comentario", length = 1000)
    private String comentario;

    @Column(name = "crea_comentario")
    private Date creaComentario;

    @ManyToOne
    @JoinColumn(name = "ID_publi")
    private publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "ID_usuario")
    private usuario usuario;


    public int getIdComen() {
        return idComen;
    }

    public void setIdComen(int idComen) {
        this.idComen = idComen;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getCreaComentario() {
        return creaComentario;
    }

    public void setCreaComentario(Date creaComentario) {
        this.creaComentario = creaComentario;
    }

    public com.example.model.publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(com.example.model.publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public com.example.model.usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(com.example.model.usuario usuario) {
        this.usuario = usuario;
    }
}
