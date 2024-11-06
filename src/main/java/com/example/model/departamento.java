package com.example.model;


import jakarta.persistence.*;

@Entity
@Table(name = "departamento")
public class departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_dept")
    private Integer idDept;

    @Column(name = "nombre_dept", length = 500)
    private String nombreDept;

    @Column(name = "rol", length = 500)
    private String rol;

    @ManyToOne
    @JoinColumn(name = "ID_colab", nullable = true)
    private colaboradores id_colaborador;

    @ManyToOne
    @JoinColumn(name = "ID_facultad", nullable = true)
    private Facultad facultad;

    public Integer getIdDept() {
        return idDept;
    }

    public void setIdDept(Integer idDept) {
        this.idDept = idDept;
    }

    public String getNombreDept() {
        return nombreDept;
    }

    public void setNombreDept(String nombreDept) {
        this.nombreDept = nombreDept;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public colaboradores getId_colaborador() {
        return id_colaborador;
    }

    public void setId_colaborador(colaboradores id_colaborador) {
        this.id_colaborador = id_colaborador;
    }
}
