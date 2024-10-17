package modelos;

import java.sql.Date;


public class evento {
    private int IDevento;
    private String notificacion;
    private boolean estado;
    private Date fecha;
    private String direccion;
    private String descripcion;
    private int IDadmin;

    public evento(int IDevento, String notificacion, boolean estado, Date fecha, String direccion, String descripcion, int IDadmin) {
        this.IDevento = IDevento;
        this.notificacion = notificacion;
        this.estado = estado;
        this.fecha = fecha;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.IDadmin = IDadmin;
    }

    public int getIDevento() {
        return IDevento;
    }

    public void setIDevento(int IDevento) {
        this.IDevento = IDevento;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public int getIDadmin() {
        return IDadmin;
    }

    public void setIDadmin(int IDadmin) {
        this.IDadmin = IDadmin;
    }
}
