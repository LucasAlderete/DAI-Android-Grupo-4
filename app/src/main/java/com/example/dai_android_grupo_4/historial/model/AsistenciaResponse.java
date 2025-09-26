package com.example.dai_android_grupo_4.historial.model;

import com.google.gson.annotations.SerializedName;

public class AsistenciaResponse {

    @SerializedName("id")
    private Long id;

    @SerializedName("usuarioId")
    private Long usuarioId;

    @SerializedName("claseId")
    private Long claseId;

    @SerializedName("clase")
    private ClaseResponse clase;

    @SerializedName("fechaAsistencia")
    private String fechaAsistencia;

    @SerializedName("fechaCheckin")
    private String fechaCheckin;

    @SerializedName("duracionMinutos")
    private Integer duracionMinutos;

    @SerializedName("calificacion")
    private Integer calificacion;

    @SerializedName("comentario")
    private String comentario;

    @SerializedName("fechaCalificacion")
    private String fechaCalificacion;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getClaseId() { return claseId; }
    public void setClaseId(Long claseId) { this.claseId = claseId; }

    public ClaseResponse getClase() { return clase; }
    public void setClase(ClaseResponse clase) { this.clase = clase; }

    public String getFechaAsistencia() { return fechaAsistencia; }
    public void setFechaAsistencia(String fechaAsistencia) { this.fechaAsistencia = fechaAsistencia; }

    public String getFechaCheckin() { return fechaCheckin; }
    public void setFechaCheckin(String fechaCheckin) { this.fechaCheckin = fechaCheckin; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getFechaCalificacion() { return fechaCalificacion; }
    public void setFechaCalificacion(String fechaCalificacion) { this.fechaCalificacion = fechaCalificacion; }
}