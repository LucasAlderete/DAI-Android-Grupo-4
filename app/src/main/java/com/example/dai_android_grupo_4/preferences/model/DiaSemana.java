package com.example.dai_android_grupo_4.preferences.model;

public enum DiaSemana {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO;

    public String toSpanish() {
        switch (this) {
            case LUNES: return "Lunes";
            case MARTES: return "Martes";
            case MIERCOLES: return "Miércoles";
            case JUEVES: return "Jueves";
            case VIERNES: return "Viernes";
            case SABADO: return "Sábado";
            case DOMINGO: return "Domingo";
            default: return "";
        }
    }

    public static DiaSemana fromString(String dia) {
        if (dia == null) return null;
        try {
            return DiaSemana.valueOf(dia.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
