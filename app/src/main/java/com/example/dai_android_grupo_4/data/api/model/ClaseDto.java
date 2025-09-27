package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ClaseDto {
    
    @SerializedName("id")
    private Long id;
    
    @SerializedName("nombre")
    private String nombre;
    
    @SerializedName("descripcion")
    private String descripcion;
    
    @SerializedName("disciplina")
    private DisciplinaDto disciplina;
    
    @SerializedName("instructor")
    private InstructorDto instructor;
    
    @SerializedName("sede")
    private SedeDto sede;
    
    @SerializedName("fechaInicio")
    private Date fechaInicio;
    
    @SerializedName("fechaFin")
    private Date fechaFin;
    
    @SerializedName("cupoMaximo")
    private Integer cupoMaximo;
    
    @SerializedName("cupoActual")
    private Integer cupoActual;
    
    @SerializedName("disponible")
    private Boolean disponible;

    // Constructores
    public ClaseDto() {}

    public ClaseDto(Long id, String nombre, String descripcion, DisciplinaDto disciplina,
                   InstructorDto instructor, SedeDto sede, Date fechaInicio, Date fechaFin,
                   Integer cupoMaximo, Integer cupoActual, Boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.disciplina = disciplina;
        this.instructor = instructor;
        this.sede = sede;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupoMaximo = cupoMaximo;
        this.cupoActual = cupoActual;
        this.disponible = disponible;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public DisciplinaDto getDisciplina() { return disciplina; }
    public void setDisciplina(DisciplinaDto disciplina) { this.disciplina = disciplina; }

    public InstructorDto getInstructor() { return instructor; }
    public void setInstructor(InstructorDto instructor) { this.instructor = instructor; }

    public SedeDto getSede() { return sede; }
    public void setSede(SedeDto sede) { this.sede = sede; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public Integer getCupoActual() { return cupoActual; }
    public void setCupoActual(Integer cupoActual) { this.cupoActual = cupoActual; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    // Métodos de utilidad
    public boolean hasAvailableSpots() {
        return cupoActual != null && cupoMaximo != null && cupoActual < cupoMaximo;
    }

    public int getAvailableSpots() {
        if (cupoMaximo == null || cupoActual == null) return 0;
        return Math.max(0, cupoMaximo - cupoActual);
    }
}
