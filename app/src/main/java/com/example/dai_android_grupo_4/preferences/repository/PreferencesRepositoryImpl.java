package com.example.dai_android_grupo_4.preferences.repository;

import androidx.annotation.NonNull;

import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.preferences.model.DisciplinaFavorita;
import com.example.dai_android_grupo_4.preferences.model.DisponibilidadHoraria;
import com.example.dai_android_grupo_4.preferences.model.SedeFavorita;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class PreferencesRepositoryImpl implements PreferencesRepository {

    private final ApiService apiService;

    @Inject
    public PreferencesRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getDisciplinasFavoritas(Callback<List<DisciplinaFavorita>> callback) {
        apiService.getDisciplinasFavoritas().enqueue(new retrofit2.Callback<List<DisciplinaFavorita>>() {
            @Override
            public void onResponse(@NonNull Call<List<DisciplinaFavorita>> call, @NonNull Response<List<DisciplinaFavorita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al obtener disciplinas favoritas"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DisciplinaFavorita>> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void addDisciplinaFavorita(Long disciplinaId, Callback<DisciplinaFavorita> callback) {
        apiService.addDisciplinaFavorita(disciplinaId).enqueue(new retrofit2.Callback<DisciplinaFavorita>() {
            @Override
            public void onResponse(@NonNull Call<DisciplinaFavorita> call, @NonNull Response<DisciplinaFavorita> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al agregar disciplina favorita"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DisciplinaFavorita> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void removeDisciplinaFavorita(Long id, Callback<Void> callback) {
        apiService.removeDisciplinaFavorita(id).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(new Exception("Error al eliminar disciplina favorita"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getSedesFavoritas(Callback<List<SedeFavorita>> callback) {
        apiService.getSedesFavoritas().enqueue(new retrofit2.Callback<List<SedeFavorita>>() {
            @Override
            public void onResponse(@NonNull Call<List<SedeFavorita>> call, @NonNull Response<List<SedeFavorita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al obtener sedes favoritas"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SedeFavorita>> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void addSedeFavorita(Long sedeId, Callback<SedeFavorita> callback) {
        apiService.addSedeFavorita(sedeId).enqueue(new retrofit2.Callback<SedeFavorita>() {
            @Override
            public void onResponse(@NonNull Call<SedeFavorita> call, @NonNull Response<SedeFavorita> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al agregar sede favorita"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SedeFavorita> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void removeSedeFavorita(Long id, Callback<Void> callback) {
        apiService.removeSedeFavorita(id).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(new Exception("Error al eliminar sede favorita"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getDisponibilidades(Callback<List<DisponibilidadHoraria>> callback) {
        apiService.getDisponibilidades().enqueue(new retrofit2.Callback<List<DisponibilidadHoraria>>() {
            @Override
            public void onResponse(@NonNull Call<List<DisponibilidadHoraria>> call, @NonNull Response<List<DisponibilidadHoraria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al obtener disponibilidades"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DisponibilidadHoraria>> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void addDisponibilidad(DisponibilidadHoraria disponibilidad, Callback<DisponibilidadHoraria> callback) {
        apiService.addDisponibilidad(disponibilidad).enqueue(new retrofit2.Callback<DisponibilidadHoraria>() {
            @Override
            public void onResponse(@NonNull Call<DisponibilidadHoraria> call, @NonNull Response<DisponibilidadHoraria> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error al agregar disponibilidad"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<DisponibilidadHoraria> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void removeDisponibilidad(Long id, Callback<Void> callback) {
        apiService.removeDisponibilidad(id).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(new Exception("Error al eliminar disponibilidad"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }
}
