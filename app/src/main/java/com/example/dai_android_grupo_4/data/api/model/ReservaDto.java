package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReservaDto {
    
    @SerializedName("id")
    private Long id;
    
    @SerializedName("usuarioId")
    private Long usuarioId;
    
    @SerializedName("claseId")
    private Long claseId;
    
    @SerializedName("clase")
    private ClaseDto clase;
    
    @SerializedName("fechaReserva")
    private Date fechaReserva;
    
    @SerializedName("estado")
    private String estado; // CONFIRMADA, CANCELADA, EXPIRADA, ASISTIDA
    
    @SerializedName("fechaCancelacion")
    private Date fechaCancelacion;

    // Constructores
    public ReservaDto() {}

    public ReservaDto(Long id, Long usuarioId, Long claseId, ClaseDto clase, 
                     Date fechaReserva, String estado, Date fechaCancelacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.claseId = claseId;
        this.clase = clase;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
        this.fechaCancelacion = fechaCancelacion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getClaseId() { return claseId; }
    public void setClaseId(Long claseId) { this.claseId = claseId; }

    public ClaseDto getClase() { return clase; }
    public void setClase(ClaseDto clase) { this.clase = clase; }

    public Date getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(Date fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(Date fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }

    // Métodos de utilidad
    public boolean isConfirmada() { return "CONFIRMADA".equals(estado); }
    public boolean isCancelada() { return "CANCELADA".equals(estado); }
    public boolean isExpirar() { return "EXPIRADA".equals(estado); }
    public boolean isAsistida() { return "ASISTIDA".equals(estado); }
    public boolean canBeCanceled() { return isConfirmada(); }
}
