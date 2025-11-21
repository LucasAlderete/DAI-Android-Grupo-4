package com.example.dai_android_grupo_4.preferences.repository;

import com.example.dai_android_grupo_4.preferences.model.DisciplinaFavorita;
import com.example.dai_android_grupo_4.preferences.model.DisponibilidadHoraria;
import com.example.dai_android_grupo_4.preferences.model.SedeFavorita;

import java.util.List;

public interface PreferencesRepository {

    interface Callback<T> {
        void onSuccess(T data);
        void onError(Throwable throwable);
    }

    // Disciplinas Favoritas
    void getDisciplinasFavoritas(Callback<List<DisciplinaFavorita>> callback);
    void addDisciplinaFavorita(Long disciplinaId, Callback<DisciplinaFavorita> callback);
    void removeDisciplinaFavorita(Long id, Callback<Void> callback);

    // Sedes Favoritas
    void getSedesFavoritas(Callback<List<SedeFavorita>> callback);
    void addSedeFavorita(Long sedeId, Callback<SedeFavorita> callback);
    void removeSedeFavorita(Long id, Callback<Void> callback);

    // Disponibilidad Horaria
    void getDisponibilidades(Callback<List<DisponibilidadHoraria>> callback);
    void addDisponibilidad(DisponibilidadHoraria disponibilidad, Callback<DisponibilidadHoraria> callback);
    void removeDisponibilidad(Long id, Callback<Void> callback);
}
