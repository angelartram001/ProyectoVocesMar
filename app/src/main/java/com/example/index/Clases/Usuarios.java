package com.example.index.Clases;

public class Usuarios {

    private String id;
    private String gmail;
    private String password;
    private String name;
    private String lastname;
    private boolean active;
    private boolean permisos;

    public Usuarios(){
    }

    public Usuarios(String id,String gmail, String password, String name, String lastname,boolean active,boolean permisos) {
        this.id= id;
        this.gmail = gmail;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
