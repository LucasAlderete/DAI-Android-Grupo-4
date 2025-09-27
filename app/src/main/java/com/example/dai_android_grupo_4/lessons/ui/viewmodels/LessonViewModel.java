package com.example.dai_android_grupo_4.lessons.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.lessons.model.Discipline;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.model.Site;
import com.example.dai_android_grupo_4.lessons.model.response.PageResponse;
import com.example.dai_android_grupo_4.lessons.repository.LessonRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LessonViewModel extends ViewModel {

    private final LessonRepository lessonRepository;

    private final MutableLiveData<List<Lesson>> lessons = new MutableLiveData<>();
    private final MutableLiveData<List<Site>> sites = new MutableLiveData<>();
    private final MutableLiveData<List<Discipline>> disciplines = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public LessonViewModel(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public LiveData<List<Lesson>> getLessons() { return lessons; }
    public LiveData<List<Site>> getSites() { return sites; }
    public LiveData<List<Discipline>> getDisciplines() { return disciplines; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void fetchLessons(Long sedeId, Long disciplinaId, String fecha) {
        isLoading.setValue(true);
        lessonRepository.getLessons(0, 100, sedeId, disciplinaId, fecha, new LessonRepository.LessonServiceCallBack() {
            @Override
            public void onSuccess(PageResponse<Lesson> pageResponse) {
                lessons.setValue(pageResponse.getContent());
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al cargar las clases: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void fetchSites() {
        lessonRepository.getSites(new LessonRepository.SiteServiceCallBack() {
            @Override
            public void onSuccess(List<Site> siteList) {
                sites.setValue(siteList);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al cargar las sedes: " + throwable.getMessage());
            }
        });
    }

    public void fetchDisciplines() {
        lessonRepository.getDisciplines(new LessonRepository.DisciplineServiceCallBack() {
            @Override
            public void onSuccess(List<Discipline> disciplineList) {
                disciplines.setValue(disciplineList);
            }

            @Override
            public void onError(Throwable throwable) {
                error.setValue("Error al cargar las disciplinas: " + throwable.getMessage());
            }
        });
    }
}
