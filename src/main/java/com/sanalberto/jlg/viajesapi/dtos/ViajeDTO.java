package com.sanalberto.jlg.viajesapi.dtos;



public class ViajeDTO {
    private String nombre;
    private String descripcion;

    public ViajeDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}