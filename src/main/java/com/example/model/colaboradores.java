package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "colaboradores")
public class colaboradores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_colab")
    private int idColaborador;

    @Column(name = "nom_colab", length = 500)
    private String nombreColaborador;
  
    @Column(name = "url_colab", length = 500)
    private String urlColaborador;

    @Column(name = "descr_colab", length = 1000)
    private String descripcionColaborador;

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }

    public String getUrlColaborador() {
        return urlColaborador;
    }

    public void setUrlColaborador(String urlColaborador) {
        this.urlColaborador = urlColaborador;
    }

    public String getDescripcionColaborador() {
        return descripcionColaborador;
    }

    public void setDescripcionColaborador(String descripcionColaborador) {
        this.descripcionColaborador = descripcionColaborador;
    }
}
