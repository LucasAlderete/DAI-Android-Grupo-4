package com.example.dai_android_grupo_4.booking.model;

public class CreateBookingRequest {
    private long claseId;

    public CreateBookingRequest(long claseId) {
        this.claseId = claseId;
    }

    public long getClaseId() {
        return claseId;
    }

    public void setClaseId(long claseId) {
        this.claseId = claseId;
    }
}
