package modelos;

public class usuario {
    private int IDusuario;
    private String NomUsuario;
    private String contra;
    private String Correo;
    private int IDrol;
    private int IDdep;

    public int getIDusuario() {
        return IDusuario;
    }

    public void setIDusuario(int IDusuario) {
        this.IDusuario = IDusuario;
    }

    public String getNomUsuario() {
        return NomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        NomUsuario = nomUsuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public int getIDrol() {
        return IDrol;
    }

    public void setIDrol(int IDrol) {
        this.IDrol = IDrol;
    }

    public int getIDdep() {
        return IDdep;
    }

    public void setIDdep(int IDdep) {
        this.IDdep = IDdep;
    }

    public usuario(int IDusuario, String nomUsuario, String contra, String correo, int IDrol, int IDdep) {
        this.IDusuario = IDusuario;
        NomUsuario = nomUsuario;
        this.contra = contra;
        Correo = correo;
        this.IDrol = IDrol;
        this.IDdep = IDdep;
    }
}
