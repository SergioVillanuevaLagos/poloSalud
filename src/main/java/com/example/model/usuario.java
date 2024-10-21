package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    @Column(name = "ID_usuario")
    private Integer idUsuario;

    @Column(name = "nom_usuario", length = 255)
    private String nombreUsuario;

    @Column(name = "contraseña", length = 255)
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "ID_rol")
    private Rol rol;

    @Column(name = "correo_elec", length = 255)
    private String correoElectronico;

    @Column(name = "ID_dept", nullable = true)
    private Integer idDepartamento;


}
