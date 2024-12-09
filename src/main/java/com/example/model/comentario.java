package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "comentario")
public class comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_comen")
    private int idComenentario;

    @Column(name = "comentario", length = 1000)
    private String comentario;

  
    @Column(name = "fecha_creacion")
    private LocalDateTime creaComentario;

    @ManyToOne
    @JoinColumn(name = "ID_publi")
    private publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "ID_usuario")
    private usuario usuario;

    public int getIdComenentario() {
        return idComenentario;
    }

    public void setIdComenentario(int idComenentario) {
        this.idComenentario = idComenentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getCreaComentario() {
        return creaComentario;
    }

    public void setCreaComentario(LocalDateTime creaComentario) {
        this.creaComentario = creaComentario;
    }

    public publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(usuario usuario) {
        this.usuario = usuario;
    }
    

}
