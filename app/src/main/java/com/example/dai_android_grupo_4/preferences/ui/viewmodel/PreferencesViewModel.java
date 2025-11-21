package com.example.dai_android_grupo_4.preferences.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.preferences.model.DisciplinaFavorita;
import com.example.dai_android_grupo_4.preferences.model.DisponibilidadHoraria;
import com.example.dai_android_grupo_4.preferences.model.SedeFavorita;
import com.example.dai_android_grupo_4.preferences.repository.PreferencesRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PreferencesViewModel extends ViewModel {

    private final PreferencesRepository preferencesRepository;

    private final MutableLiveData<List<DisciplinaFavorita>> disciplinasFavoritas = new MutableLiveData<>();
    private final MutableLiveData<List<SedeFavorita>> sedesFavoritas = new MutableLiveData<>();
    private final MutableLiveData<List<DisponibilidadHoraria>> disponibilidades = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();

    @Inject
    public PreferencesViewModel(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    public LiveData<List<DisciplinaFavorita>> getDisciplinasFavoritas() {
        return disciplinasFavoritas;
    }

    public LiveData<List<SedeFavorita>> getSedesFavoritas() {
        return sedesFavoritas;
    }

    public LiveData<List<DisponibilidadHoraria>> getDisponibilidades() {
        return disponibilidades;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    // Disciplinas Favoritas
    public void fetchDisciplinasFavoritas() {
        isLoading.setValue(true);
        preferencesRepository.getDisciplinasFavoritas(new PreferencesRepository.Callback<List<DisciplinaFavorita>>() {
            @Override
            public void onSuccess(List<DisciplinaFavorita> data) {
                disciplinasFavoritas.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al cargar disciplinas favoritas: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void addDisciplinaFavorita(Long disciplinaId) {
        isLoading.setValue(true);
        preferencesRepository.addDisciplinaFavorita(disciplinaId, new PreferencesRepository.Callback<DisciplinaFavorita>() {
            @Override
            public void onSuccess(DisciplinaFavorita data) {
                successMessage.setValue("Disciplina agregada a favoritos");
                fetchDisciplinasFavoritas();
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al agregar disciplina: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void removeDisciplinaFavorita(Long id) {
        isLoading.setValue(true);
        preferencesRepository.removeDisciplinaFavorita(id, new PreferencesRepository.Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                successMessage.setValue("Disciplina eliminada de favoritos");
                fetchDisciplinasFavoritas();
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al eliminar disciplina: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    // Sedes Favoritas
    public void fetchSedesFavoritas() {
        isLoading.setValue(true);
        preferencesRepository.getSedesFavoritas(new PreferencesRepository.Callback<List<SedeFavorita>>() {
            @Override
            public void onSuccess(List<SedeFavorita> data) {
                sedesFavoritas.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al cargar sedes favoritas: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void addSedeFavorita(Long sedeId) {
        isLoading.setValue(true);
        preferencesRepository.addSedeFavorita(sedeId, new PreferencesRepository.Callback<SedeFavorita>() {
            @Override
            public void onSuccess(SedeFavorita data) {
                successMessage.setValue("Sede agregada a favoritos");
                fetchSedesFavoritas();
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al agregar sede: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void removeSedeFavorita(Long id) {
        isLoading.setValue(true);
        preferencesRepository.removeSedeFavorita(id, new PreferencesRepository.Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                successMessage.setValue("Sede eliminada de favoritos");
                fetchSedesFavoritas();
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al eliminar sede: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    // Disponibilidad Horaria
    public void fetchDisponibilidades() {
        isLoading.setValue(true);
        preferencesRepository.getDisponibilidades(new PreferencesRepository.Callback<List<DisponibilidadHoraria>>() {
            @Override
            public void onSuccess(List<DisponibilidadHoraria> data) {
                disponibilidades.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al cargar disponibilidades: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void addDisponibilidad(DisponibilidadHoraria disponibilidad) {
        isLoading.setValue(true);
        preferencesRepository.addDisponibilidad(disponibilidad, new PreferencesRepository.Callback<DisponibilidadHoraria>() {
            @Override
            public void onSuccess(DisponibilidadHoraria data) {
                successMessage.setValue("Disponibilidad agregada");
                fetchDisponibilidades();
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al agregar disponibilidad: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void removeDisponibilidad(Long id) {
        isLoading.setValue(true);
        preferencesRepository.removeDisponibilidad(id, new PreferencesRepository.Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                successMessage.setValue("Disponibilidad eliminada");
                fetchDisponibilidades();
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al eliminar disponibilidad: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }
}
