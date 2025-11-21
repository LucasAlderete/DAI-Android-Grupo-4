package com.example.dai_android_grupo_4.preferences.model;

import com.google.gson.annotations.SerializedName;

public class DisponibilidadHoraria {
    private Long id;

    @SerializedName("diaSemana")
    private String diaSemanaString; // Para JSON

    private String horaInicio; // "15:00:00"
    private String horaFin; // "18:00:00"

    public DisponibilidadHoraria() {}

    public DisponibilidadHoraria(DiaSemana diaSemana, String horaInicio, String horaFin) {
        this.diaSemanaString = diaSemana.name();
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaSemana getDiaSemana() {
        return DiaSemana.fromString(diaSemanaString);
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemanaString = diaSemana != null ? diaSemana.name() : null;
    }

    public String getDiaSemanaString() {
        return diaSemanaString;
    }

    public void setDiaSemanaString(String diaSemanaString) {
        this.diaSemanaString = diaSemanaString;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisponibilidadHoraria that = (DisponibilidadHoraria) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
