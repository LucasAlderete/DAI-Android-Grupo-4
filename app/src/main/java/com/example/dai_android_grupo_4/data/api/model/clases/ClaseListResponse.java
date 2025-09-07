package com.example.dai_android_grupo_4.data.api.model.clases;

import com.example.dai_android_grupo_4.model.Clase;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClaseListResponse {

    @SerializedName("content") // La API paginada devuelve la lista en la clave "content"
    private List<ClaseDetailResponse> clases;

    // Constructor vacío requerido por Gson
    public ClaseListResponse() {}

    // Constructor con parámetros
    public ClaseListResponse(List<ClaseDetailResponse> clases) {
        this.clases = clases;
    }

    // Getter
    public List<ClaseDetailResponse> getClases() {
        return clases;
    }

    // Setter
    public void setClases(List<ClaseDetailResponse> clases) {
        this.clases = clases;
    }

    public List<Clase> toClases() {
        if (clases == null) {
            return new ArrayList<>();
        }
        return clases.stream().map(ClaseDetailResponse::toClase).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ClaseListResponse{" +
                "clases=" + clases +
                '}';
    }
}
