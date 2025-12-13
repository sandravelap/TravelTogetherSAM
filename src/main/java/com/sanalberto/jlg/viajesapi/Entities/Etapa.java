package com.sanalberto.jlg.viajesapi.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Time;

@Entity
@Table(name = "Etapa", schema = "traveltogether")
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idViaje", nullable = false)
    private Viaje idViaje;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idDestino", nullable = false)
    private Destino idDestino;

    @Column(name = "horaInicio", nullable = false)
    private Time horaInicio;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Viaje getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Viaje idViaje) {
        this.idViaje = idViaje;
    }

    public Destino getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Destino idDestino) {
        this.idDestino = idDestino;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

}