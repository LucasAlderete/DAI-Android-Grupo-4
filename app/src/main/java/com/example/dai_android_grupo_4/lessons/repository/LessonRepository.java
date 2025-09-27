package com.example.dai_android_grupo_4.lessons.repository;

import com.example.dai_android_grupo_4.lessons.model.Discipline;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.model.Site;
import com.example.dai_android_grupo_4.lessons.model.response.PageResponse;

import java.util.List;

public interface LessonRepository {

    interface LessonServiceCallBack {
        void onSuccess(PageResponse<Lesson> pageResponse);
        void onError(Throwable error);
    }

    interface LessonDetailCallback {
        void onSuccess(Lesson lesson);
        void onError(Throwable throwable);
    }

    interface SiteServiceCallBack {
        void onSuccess(List<Site> sites);
        void onError(Throwable error);
    }

    interface DisciplineServiceCallBack {
        void onSuccess(List<Discipline> disciplines);
        void onError(Throwable error);
    }

    void getLessons(
            int page,
            int size,
            Long siteId,
            Long disciplineId,
            String date,
            LessonServiceCallBack callback
    );

    void getLessonById(Long lessonId, LessonDetailCallback callback);

    void getSites(SiteServiceCallBack callback);

    void getDisciplines(DisciplineServiceCallBack callback);
}
