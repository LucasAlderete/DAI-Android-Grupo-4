package com.example.dai_android_grupo_4.booking.repository;

import com.example.dai_android_grupo_4.data.api.model.*;

import java.util.List;

public interface ClaseRepository {
    
    interface ClaseCallback {
        void onSuccess(List<ClaseDto> clases);
        void onError(String error);
    }
    
    interface SedeCallback {
        void onSuccess(List<SedeDto> sedes);
        void onError(String error);
    }
    
    interface InstructorCallback {
        void onSuccess(List<InstructorDto> instructores);
        void onError(String error);
    }
    
    interface DisciplinaCallback {
        void onSuccess(List<DisciplinaDto> disciplinas);
        void onError(String error);
    }

    void getClases(Long sedeId, Long disciplinaId, String fechaInicio, String fechaFin, 
                   Integer page, Integer size, ClaseCallback callback);
    
    void getClasePorId(Long id, ClaseCallback callback);
    
    void getSedes(SedeCallback callback);
    
    void getInstructores(InstructorCallback callback);
    
    void getDisciplinas(DisciplinaCallback callback);
}
