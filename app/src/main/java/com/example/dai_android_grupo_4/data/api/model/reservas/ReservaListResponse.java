package com.example.dai_android_grupo_4.data.api.model.reservas;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReservaListResponse {
    @SerializedName("reservas")
    private List<ReservaDetailResponse> reservas;
    
    // Constructor vacío requerido por Gson
    public ReservaListResponse() {}
    
    // Constructor con parámetros
    public ReservaListResponse(List<ReservaDetailResponse> reservas) {
        this.reservas = reservas;
    }
    
    // Getter
    public List<ReservaDetailResponse> getReservas() {
        return reservas;
    }
    
    // Setter
    public void setReservas(List<ReservaDetailResponse> reservas) {
        this.reservas = reservas;
    }
    
    @Override
    public String toString() {
        return "ReservaListResponse{" +
                "reservas=" + reservas +
                '}';
    }
}
