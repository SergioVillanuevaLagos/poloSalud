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


    

}
