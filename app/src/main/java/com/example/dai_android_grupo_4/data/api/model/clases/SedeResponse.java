package com.example.dai_android_grupo_4.data.api.model.clases;

import com.example.dai_android_grupo_4.model.Sede;

public class SedeResponse {
    private Long id;
    private String nombre;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sede toSede() {
        Sede sede = new Sede();
        sede.setId(this.id);
        sede.setNombre(this.nombre);
        return sede;
    }
}
