package com.example.dai_android_grupo_4.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Reserva implements Parcelable {
    private String clase;
    private String horario;
    private String profesor;

    public Reserva(String clase, String horario, String profesor) {
        this.clase = clase;
        this.horario = horario;
        this.profesor = profesor;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getClase() {
        return clase;
    }

    public String getHorario() {
        return horario;
    }

    public String getProfesor() {
        return profesor;
    }

    public Reserva(Parcel in) {
    }

    public static final Creator<Reserva> CREATOR = new Creator<Reserva>() {
        @Override
        public Reserva createFromParcel(Parcel in) {
            return new Reserva(in);
        }

        @Override
        public Reserva[] newArray(int size) {
            return new Reserva[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}
