package com.sanalberto.jlg.viajesapi.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Destino", schema = "traveltogether")
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "coordX", nullable = false)
    private Double coordX;

    @Column(name = "coordY", nullable = false)
    private Double coordY;

    @Column(name = "dificultad", nullable = false)
    private Integer dificultad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getCoordX() {
        return coordX;
    }

    public void setCoordX(Double coordX) {
        this.coordX = coordX;
    }

    public Double getCoordY() {
        return coordY;
    }

    public void setCoordY(Double coordY) {
        this.coordY = coordY;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
    }

}