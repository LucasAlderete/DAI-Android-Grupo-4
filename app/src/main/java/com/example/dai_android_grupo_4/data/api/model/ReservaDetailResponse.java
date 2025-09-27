package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

public class ReservaDetailResponse {
    @SerializedName("id")
    private int id;
    
    @SerializedName("clase")
    private String clase;
    
    @SerializedName("disciplina")
    private String disciplina;
    
    @SerializedName("horario")
    private String horario;
    
    @SerializedName("profesor")
    private String profesor;
    
    @SerializedName("sede")
    private String sede;
    
    @SerializedName("fecha")
    private String fecha;

    public ReservaDetailResponse() {}

    public ReservaDetailResponse(int id, String clase, String disciplina, String horario, 
                                String profesor, String sede, String fecha) {
        this.id = id;
        this.clase = clase;
        this.disciplina = disciplina;
        this.horario = horario;
        this.profesor = profesor;
        this.sede = sede;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }
    
    public String getClase() {
        return clase;
    }
    
    public String getDisciplina() {
        return disciplina;
    }
    
    public String getHorario() {
        return horario;
    }
    
    public String getProfesor() {
        return profesor;
    }
    
    public String getSede() {
        return sede;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setClase(String clase) {
        this.clase = clase;
    }
    
    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
    
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    
    public void setSede(String sede) {
        this.sede = sede;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public String toString() {
        return "ReservaDetailResponse{" +
                "id=" + id +
                ", clase='" + clase + '\'' +
                ", disciplina='" + disciplina + '\'' +
                ", horario='" + horario + '\'' +
                ", profesor='" + profesor + '\'' +
                ", sede='" + sede + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
