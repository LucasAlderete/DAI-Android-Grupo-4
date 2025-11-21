package com.example.dai_android_grupo_4.preferences.model;

public class DisciplinaFavorita {
    private Long id;
    private Long disciplinaId;
    private Long usuarioId;
    private String disciplinaNombre;

    public DisciplinaFavorita() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDisciplinaNombre() {
        return disciplinaNombre;
    }

    public void setDisciplinaNombre(String disciplinaNombre) {
        this.disciplinaNombre = disciplinaNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisciplinaFavorita that = (DisciplinaFavorita) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
