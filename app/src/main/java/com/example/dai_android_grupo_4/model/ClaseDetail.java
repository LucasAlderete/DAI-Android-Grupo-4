package com.example.dai_android_grupo_4.model;

import com.example.dai_android_grupo_4.data.api.model.clases.ClaseDetailResponse;

public class ClaseDetail {
    private Long id;
    private Disciplina disciplina;
    private String fechaHora;
    private int duracionMinutos;
    private int cupoDisponible;
    private String nombreInstructor;
    private Sede sede;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public static ClaseDetail toClaseDetail(ClaseDetailResponse response) {
        ClaseDetail detail = new ClaseDetail();
        Disciplina disciplina = new Disciplina();
        disciplina.setId(response.getDisciplina().getId());
        disciplina.setNombre(response.getDisciplina().getNombre());

        detail.setId(response.getId());
        detail.setDisciplina(disciplina);
        detail.setFechaHora(response.getFechaHora());
        detail.setDuracionMinutos(response.getDuracionMinutos());
        detail.setCupoDisponible(response.getCupoDisponible());
        detail.setNombreInstructor(response.getNombreInstructor());

        // Usar el método toSede() para un mapeo correcto que incluye la dirección
        if (response.getSede() != null) {
            detail.setSede(response.getSede().toSede());
        }

        return detail;
    }
}
