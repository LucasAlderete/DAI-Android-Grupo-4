package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

public class SedeDto {
    
    @SerializedName("id")
    private Long id;
    
    @SerializedName("nombre")
    private String nombre;
    
    @SerializedName("direccion")
    private String direccion;
    
    @SerializedName("telefono")
    private String telefono;

    // Constructores
    public SedeDto() {}

    public SedeDto(Long id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
