package modelos;

public class departamento {
    private int IDdepto;
    private int IDasocia;
    private String nombredepto;
    private String roldepto;
    private int IDfacultad;

    public departamento(int IDdepto, int IDasocia, String nombredepto, String roldepto, int IDfacultad) {
        this.IDdepto = IDdepto;
        this.IDasocia = IDasocia;
        this.nombredepto = nombredepto;
        this.roldepto = roldepto;
        this.IDfacultad = IDfacultad;
    }

    public int getIDdepto() {
        return IDdepto;
    }

    public void setIDdepto(int IDdepto) {
        this.IDdepto = IDdepto;
    }

    public int getIDasocia() {
        return IDasocia;
    }

    public void setIDasocia(int IDasocia) {
        this.IDasocia = IDasocia;
    }

    public String getNombredepto() {
        return nombredepto;
    }

    public void setNombredepto(String nombredepto) {
        this.nombredepto = nombredepto;
    }

    public String getRoldepto() {
        return roldepto;
    }

    public void setRoldepto(String roldepto) {
        this.roldepto = roldepto;
    }

    public int getIDfacultad() {
        return IDfacultad;
    }

    public void setIDfacultad(int IDfacultad) {
        this.IDfacultad = IDfacultad;
    }
}
