package com.example.dai_android_grupo_4.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.data.api.model.clases.ClaseListResponse;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseServiceCallBack;
import com.example.dai_android_grupo_4.model.Clase;
import com.example.dai_android_grupo_4.model.Disciplina;
import com.example.dai_android_grupo_4.model.Sede;
import com.example.dai_android_grupo_4.services.clases.ClaseService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ClasesViewModel extends ViewModel {

    private final ClaseService claseService;

    // LiveData para la lista de clases a mostrar
    private final MutableLiveData<List<Clase>> clases = new MutableLiveData<>();
    // LiveData para las opciones de los filtros
    private final MutableLiveData<List<Sede>> sedesOptions = new MutableLiveData<>();
    private final MutableLiveData<List<Disciplina>> disciplinasOptions = new MutableLiveData<>();
    // LiveData para el estado de la UI
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public ClasesViewModel(ClaseService claseService) {
        this.claseService = claseService;
    }

    // Getters para que la Activity observe los datos
    public LiveData<List<Clase>> getClases() { return clases; }
    public LiveData<List<Sede>> getSedesOptions() { return sedesOptions; }
    public LiveData<List<Disciplina>> getDisciplinasOptions() { return disciplinasOptions; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void fetchClases(Long sedeId, Long disciplinaId, String fecha) {
        isLoading.setValue(true);
        claseService.getAllClases(0, 100, sedeId, disciplinaId, fecha, new ClaseServiceCallBack() {
            @Override
            public void onSuccess(ClaseListResponse claseListResponse) {
                List<Clase> fetchedClases = claseListResponse.toClases();
                clases.setValue(fetchedClases);

                // Si es la carga inicial (sin filtros), extraemos las opciones para los spinners
                if (sedeId == null && disciplinaId == null && fecha == null) {
                    extractFilterOptions(fetchedClases);
                }
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al cargar las clases: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    private void extractFilterOptions(List<Clase> clases) {
        Set<Sede> sedesSet = new HashSet<>();
        Set<Disciplina> disciplinasSet = new HashSet<>();

        for (Clase clase : clases) {
            sedesSet.add(clase.getSede());
            disciplinasSet.add(clase.getDisciplina());
        }

        sedesOptions.setValue(new ArrayList<>(sedesSet));
        disciplinasOptions.setValue(new ArrayList<>(disciplinasSet));
    }
}
