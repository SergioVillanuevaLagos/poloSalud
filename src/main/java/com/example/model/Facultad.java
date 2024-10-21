package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "facultad")
public class Facultad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_facultad")
    private Integer idFacultad;

    @Column(name = "nom_facultad", length = 500)
    private String nombreFacultad;

    @Column(name = "descrp_facultad", length = 500)
    private String descripcionFacultad;

    @ManyToOne
    @JoinColumn(name = "ID_dept", nullable = false)
    private departamento departamento;

    public Integer getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(Integer idFacultad) {
        this.idFacultad = idFacultad;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

    public String getDescripcionFacultad() {
        return descripcionFacultad;
    }

    public void setDescripcionFacultad(String descripcionFacultad) {
        this.descripcionFacultad = descripcionFacultad;
    }

    public com.example.model.departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(com.example.model.departamento departamento) {
        this.departamento = departamento;
    }
}
