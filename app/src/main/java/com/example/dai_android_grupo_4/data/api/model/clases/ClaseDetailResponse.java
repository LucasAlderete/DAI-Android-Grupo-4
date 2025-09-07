package com.example.dai_android_grupo_4.data.api.model.clases;

import com.example.dai_android_grupo_4.model.Clase;
import com.google.gson.annotations.SerializedName;

public class ClaseDetailResponse {

    private Long id;
    private DisciplinaResponse disciplina;
    @SerializedName("fechaHora")
    private String fechaHora;
    @SerializedName("nombreInstructor")
    private String nombreInstructor;
    private SedeResponse sede;

    @SerializedName("duracionMinutos")
    private int duracionMinutos;

    @SerializedName("cupoDisponible")
    private int cupoDisponible;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisciplinaResponse getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaResponse disciplina) {
        this.disciplina = disciplina;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public int getDuracionMinutos() { return duracionMinutos; }

    public int getCupoDisponible() { return cupoDisponible; }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

    public SedeResponse getSede() {
        return sede;
    }

    public void setSede(SedeResponse sede) {
        this.sede = sede;
    }

    public Clase toClase() {
        Clase clase = new Clase();
        clase.setId(this.id);
        clase.setFechaHora(this.fechaHora);
        clase.setNombreInstructor(this.nombreInstructor);
        if (this.disciplina != null) {
            clase.setDisciplina(this.disciplina.toDisciplina());
        }
        if (this.sede != null) {
            clase.setSede(this.sede.toSede());
        }
        return clase;
    }
}
