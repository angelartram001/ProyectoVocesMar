package com.example.index.Clases;

public class ClassEventos {

    //Variable imagen aun en proceso
    private String codex;
    private String name;
    private String descripcion;
    private String ubicacion;
    private String foto;
    private String hora;
    private String fecha;

    public ClassEventos(){
    }

    public ClassEventos(String codex,String name, String descripcion , String ubicacion, String foto, String hora, String fecha) {
        this.codex = codex;
        this.name = name;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.foto = foto;
        this.hora = hora;
        this.fecha = fecha;
    }

    public String getCodex() {
        return codex;
    }

    public void setCodex(String codex) {
        this.codex = codex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
