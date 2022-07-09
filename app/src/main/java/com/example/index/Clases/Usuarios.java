package com.example.index.Clases;

public class Usuarios {

    private String id;
    private String name;
    private String gmail;
    private String password;
    private int edad;
    private String postulacion;
    private boolean active;
    private boolean permisos;

    //Vacio
    public Usuarios(){
    }

    //Recycle
    public Usuarios(String name,String gmail){
        this.name = name;
        this.gmail = gmail;
    }
    //Standar


    public Usuarios(String id, String name, String gmail, String password, int edad ,String postulacion, boolean active, boolean permisos) {
        this.id = id;
        this.name = name;
        this.gmail = gmail;
        this.password = password;
        this.edad = edad;
        this.postulacion = postulacion;
        this.active = active;
        this.permisos = permisos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(String postulacion) {
        this.postulacion = postulacion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPermisos() {
        return permisos;
    }

    public void setPermisos(boolean permisos) {
        this.permisos = permisos;
    }

}
