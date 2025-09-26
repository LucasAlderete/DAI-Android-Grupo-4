package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReservaListResponse {
    @SerializedName("reservas")
    private List<ReservaDetailResponse> reservas;

    public ReservaListResponse() {}

    public ReservaListResponse(List<ReservaDetailResponse> reservas) {
        this.reservas = reservas;
    }

    public List<ReservaDetailResponse> getReservas() {
        return reservas;
    }

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
