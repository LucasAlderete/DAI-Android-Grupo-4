package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

public class CrearReservaDto {
    
    @SerializedName("claseId")
    private Long claseId;

    // Constructores
    public CrearReservaDto() {}

    public CrearReservaDto(Long claseId) {
        this.claseId = claseId;
    }

    // Getters y Setters
    public Long getClaseId() { return claseId; }
    public void setClaseId(Long claseId) { this.claseId = claseId; }
}
