package com.sanalberto.jlg.viajesapi.dtos;

import java.sql.Date;

public class MisViajesDTO {
    //Atributos de clase
    private String nombre;
    private String descripcion;
    private int participantes;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean tabaco;
    private String mascota;

    // Constructor vacío

    public MisViajesDTO() {
    }

    // Getters y setters
    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public boolean isTabaco() {
        return tabaco;
    }

    public void setTabaco(boolean tabaco) {
        this.tabaco = tabaco;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getParticipantes() {
        return participantes;
    }

    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
