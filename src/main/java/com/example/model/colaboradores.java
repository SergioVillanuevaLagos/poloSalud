package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "colaboradores")
public class colaboradores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colab")
    private int idColaborador;

    @Column(name = "nom_colab", length = 500)
    private String nombreColaborador;

    @Column(name = "res_colab", length = 500)
    private String resumenColaborador;

    @Column(name = "benef_internos", length = 1000)
    private String beneficiosInternos;

    @Column(name = "benef_externos", length = 1000, nullable = false)
    private String beneficiosExternos;

    @Column(name = "actividades", length = 1000, nullable = false)
    private String actividades;

    @Column(name = "tipo_finan", length = 1000, nullable = false)
    private String tipoFinanciamiento;

    @Column(name = "monto", nullable = false)
    private int monto;

    @Column(name = "uso_monto", length = 1000, nullable = false)
    private String usoMonto;

    @Column(name = "evidencias", length = 1000, nullable = false)
    private String evidencias;

    // Getters y Setters
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

    public String getResumenColaborador() {
        return resumenColaborador;
    }

    public void setResumenColaborador(String resumenColaborador) {
        this.resumenColaborador = resumenColaborador;
    }

    public String getBeneficiosInternos() {
        return beneficiosInternos;
    }

    public void setBeneficiosInternos(String beneficiosInternos) {
        this.beneficiosInternos = beneficiosInternos;
    }

    public String getBeneficiosExternos() {
        return beneficiosExternos;
    }

    public void setBeneficiosExternos(String beneficiosExternos) {
        this.beneficiosExternos = beneficiosExternos;
    }

    public String getActividades() {
        return actividades;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }

    public String getTipoFinanciamiento() {
        return tipoFinanciamiento;
    }

    public void setTipoFinanciamiento(String tipoFinanciamiento) {
        this.tipoFinanciamiento = tipoFinanciamiento;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getUsoMonto() {
        return usoMonto;
    }

    public void setUsoMonto(String usoMonto) {
        this.usoMonto = usoMonto;
    }

    public String getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(String evidencias) {
        this.evidencias = evidencias;
    }
}
