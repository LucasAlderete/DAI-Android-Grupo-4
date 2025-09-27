package com.example.dai_android_grupo_4.lessons.repository;

import com.example.dai_android_grupo_4.lessons.api.LessonService;
import com.example.dai_android_grupo_4.lessons.model.Discipline;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.model.Site;
import com.example.dai_android_grupo_4.lessons.model.response.PageResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonRepositoryImpl implements LessonRepository {

    private final LessonService lessonService;

    @Inject
    public LessonRepositoryImpl(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public void getLessons(int page, int size, Long siteId, Long disciplineId, String date, LessonServiceCallBack callback) {
        lessonService.getAllLessons(page, size, siteId, disciplineId, date).enqueue(new Callback<PageResponse<Lesson>>() {
            @Override
            public void onResponse(Call<PageResponse<Lesson>> call, Response<PageResponse<Lesson>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error fetching lessons"));
                }
            }

            @Override
            public void onFailure(Call<PageResponse<Lesson>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getLessonById(Long lessonId, LessonDetailCallback callback) {
        lessonService.getLessonById(lessonId).enqueue(new Callback<Lesson>() {
            @Override
            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error fetching lesson detail"));
                }
            }

            @Override
            public void onFailure(Call<Lesson> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getSites(SiteServiceCallBack callback) {
        lessonService.getSites().enqueue(new Callback<List<Site>>() {
            @Override
            public void onResponse(Call<List<Site>> call, Response<List<Site>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error fetching sites"));
                }
            }

            @Override
            public void onFailure(Call<List<Site>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void getDisciplines(DisciplineServiceCallBack callback) {
        lessonService.getDisciplines().enqueue(new Callback<List<Discipline>>() {
            @Override
            public void onResponse(Call<List<Discipline>> call, Response<List<Discipline>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error fetching disciplines"));
                }
            }

            @Override
            public void onFailure(Call<List<Discipline>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
