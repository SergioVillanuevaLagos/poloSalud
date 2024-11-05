package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "eventos")
public class evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_evento")
    private int ID_evento;

    @Column(name = "fecha_envent")
    private LocalDate fecha_evento;

    @Column(name = "notificacion", length = 500)
    private String notificacion;

    @Column(name = "direccion", length = 500)
    private String direccion;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;



    @Column(name = "ID_admin")
    private int idAdmin;



    public int getID_evento() {
        return ID_evento;
    }

    public void setID_evento(int ID_evento) {
        this.ID_evento = ID_evento;
    }

    public LocalDate getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(LocalDate fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
}




