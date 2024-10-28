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

    // Constructores, getters y setters

    public usuario() {
    }

    public usuario(Integer idUsuario, String nombreUsuario, String contraseña, Rol rol, String correoElectronico, Integer idDepartamento) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.rol = rol;
        this.correoElectronico = correoElectronico;
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
}