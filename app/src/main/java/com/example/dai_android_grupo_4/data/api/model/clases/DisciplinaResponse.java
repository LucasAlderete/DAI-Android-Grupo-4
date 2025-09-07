package com.example.dai_android_grupo_4.data.api.model.clases;

import com.example.dai_android_grupo_4.model.Disciplina;

public class DisciplinaResponse {
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

    public Disciplina toDisciplina() {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(this.id);
        disciplina.setNombre(this.nombre);
        return disciplina;
    }
}
