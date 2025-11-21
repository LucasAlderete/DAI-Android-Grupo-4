package com.example.dai_android_grupo_4.lessons.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.lessons.model.Discipline;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.model.Site;
import com.example.dai_android_grupo_4.lessons.model.response.PageResponse;
import com.example.dai_android_grupo_4.lessons.repository.LessonRepository;
import com.example.dai_android_grupo_4.preferences.model.DisciplinaFavorita;
import com.example.dai_android_grupo_4.preferences.model.DisponibilidadHoraria;
import com.example.dai_android_grupo_4.preferences.model.SedeFavorita;
import com.example.dai_android_grupo_4.preferences.repository.PreferencesRepository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LessonViewModel extends ViewModel {

    private final LessonRepository lessonRepository;
    private final PreferencesRepository preferencesRepository;

    private final MutableLiveData<List<Lesson>> lessons = new MutableLiveData<>();
    private final MutableLiveData<List<Site>> sites = new MutableLiveData<>();
    private final MutableLiveData<List<Discipline>> disciplines = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public LessonViewModel(LessonRepository lessonRepository, PreferencesRepository preferencesRepository) {
        this.lessonRepository = lessonRepository;
        this.preferencesRepository = preferencesRepository;
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

    // NUEVA FUNCIÓN: Fetch con ordenamiento por preferencias
    public void fetchLessonsOrdered(Long sedeId, Long disciplinaId, String fecha) {
        isLoading.setValue(true);

        // 1. Obtener disciplinas favoritas
        preferencesRepository.getDisciplinasFavoritas(new PreferencesRepository.Callback<List<DisciplinaFavorita>>() {
            @Override
            public void onSuccess(List<DisciplinaFavorita> disciplinasFav) {
                // 2. Obtener sedes favoritas
                preferencesRepository.getSedesFavoritas(new PreferencesRepository.Callback<List<SedeFavorita>>() {
                    @Override
                    public void onSuccess(List<SedeFavorita> sedesFav) {
                        // 3. Obtener disponibilidades horarias
                        preferencesRepository.getDisponibilidades(new PreferencesRepository.Callback<List<DisponibilidadHoraria>>() {
                            @Override
                            public void onSuccess(List<DisponibilidadHoraria> disponibilidades) {
                                // 4. Obtener clases
                                lessonRepository.getLessons(0, 100, sedeId, disciplinaId, fecha,
                                        new LessonRepository.LessonServiceCallBack() {
                                            @Override
                                            public void onSuccess(PageResponse<Lesson> pageResponse) {
                                                // 5. Ordenar por relevancia
                                                List<Lesson> sorted = ordenarPorPreferencias(
                                                        pageResponse.getContent(),
                                                        disciplinasFav,
                                                        sedesFav,
                                                        disponibilidades
                                                );
                                                lessons.setValue(sorted);
                                                isLoading.setValue(false);
                                            }

                                            @Override
                                            public void onError(Throwable throwable) {
                                                error.setValue("Error al cargar las clases: " + throwable.getMessage());
                                                isLoading.setValue(false);
                                            }
                                        }
                                );
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                // Si falla obtener disponibilidades, continuar sin ellas
                                lessonRepository.getLessons(0, 100, sedeId, disciplinaId, fecha,
                                        new LessonRepository.LessonServiceCallBack() {
                                            @Override
                                            public void onSuccess(PageResponse<Lesson> pageResponse) {
                                                List<Lesson> sorted = ordenarPorPreferencias(
                                                        pageResponse.getContent(),
                                                        disciplinasFav,
                                                        sedesFav,
                                                        Collections.emptyList()
                                                );
                                                lessons.setValue(sorted);
                                                isLoading.setValue(false);
                                            }

                                            @Override
                                            public void onError(Throwable throwable) {
                                                error.setValue("Error al cargar las clases: " + throwable.getMessage());
                                                isLoading.setValue(false);
                                            }
                                        }
                                );
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // Si falla obtener sedes favoritas, mostrar sin ordenar
                        fetchLessons(sedeId, disciplinaId, fecha);
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                // Si falla obtener disciplinas favoritas, mostrar sin ordenar
                fetchLessons(sedeId, disciplinaId, fecha);
            }
        });
    }

    // LÓGICA DE ORDENAMIENTO
    private List<Lesson> ordenarPorPreferencias(List<Lesson> lessons,
                                                 List<DisciplinaFavorita> disciplinasFav,
                                                 List<SedeFavorita> sedesFav,
                                                 List<DisponibilidadHoraria> disponibilidades) {
        // Calcular score para cada clase
        for (Lesson lesson : lessons) {
            int score = 0;

            // +1 si disciplina es favorita
            for (DisciplinaFavorita df : disciplinasFav) {
                if (lesson.getDisciplina() != null &&
                        lesson.getDisciplina().getId() != null &&
                        lesson.getDisciplina().getId().equals(df.getDisciplinaId())) {
                    score += 1;
                    break;
                }
            }

            // +1 si sede es favorita
            for (SedeFavorita sf : sedesFav) {
                if (lesson.getSede() != null &&
                        lesson.getSede().getId() != null &&
                        lesson.getSede().getId().equals(sf.getSedeId())) {
                    score += 1;
                    break;
                }
            }

            // +1 si coincide con disponibilidad horaria
            for (DisponibilidadHoraria disp : disponibilidades) {
                if (lesson.getFechaInicio() != null && !lesson.getFechaInicio().isEmpty()) {
                    try {
                        String fechaStr = lesson.getFechaInicio();
                        // Extraer la fecha (antes de la T)
                        String fechaPart = fechaStr.split("T")[0]; // "2024-03-15"
                        // Extraer la hora (después de la T)
                        String horaPart = fechaStr.split("T")[1]; // "10:00:00"

                        // Convertir fecha a día de la semana
                        java.time.LocalDate fecha = java.time.LocalDate.parse(fechaPart);
                        java.time.DayOfWeek dayOfWeek = fecha.getDayOfWeek();

                        // Convertir DayOfWeek de Java a nuestro enum DiaSemana
                        String diaStr = convertirDayOfWeekADiaSemana(dayOfWeek);
                        com.example.dai_android_grupo_4.preferences.model.DiaSemana diaClase =
                                com.example.dai_android_grupo_4.preferences.model.DiaSemana.fromString(diaStr);

                        // Comparar día de la clase con día de la disponibilidad
                        if (diaClase != null && diaClase.equals(disp.getDiaSemana())) {
                            // Comparar horarios
                            String horaClase = horaPart.substring(0, 5); // "10:00"
                            String horaDispInicio = disp.getHoraInicio().substring(0, 5); // "15:00"
                            String horaDispFin = disp.getHoraFin().substring(0, 5); // "18:00"

                            // Comparar strings de hora (funciona porque están en formato HH:mm)
                            if (horaClase.compareTo(horaDispInicio) >= 0 &&
                                    horaClase.compareTo(horaDispFin) <= 0) {
                                // ¡Coincide! Dar +1 punto
                                score += 1;
                                break; // No seguir buscando, ya encontramos una coincidencia
                            }
                        }
                    } catch (Exception e) {
                        // Si hay error parseando la fecha, continuar sin dar puntos
                    }
                }
            }

            lesson.setRelevanceScore(score);
        }

        // Ordenar: score DESC, luego por fecha ASC
        Collections.sort(lessons, (a, b) -> {
            int scoreCompare = Integer.compare(b.getRelevanceScore(), a.getRelevanceScore());
            if (scoreCompare != 0) {
                return scoreCompare;
            }
            // Si tienen mismo score, ordenar por fecha
            if (a.getFechaInicio() != null && b.getFechaInicio() != null) {
                return a.getFechaInicio().compareTo(b.getFechaInicio());
            }
            return 0;
        });

        return lessons;
    }

    // Método helper para convertir DayOfWeek de Java a nuestro enum DiaSemana
    private String convertirDayOfWeekADiaSemana(java.time.DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "LUNES";
            case TUESDAY: return "MARTES";
            case WEDNESDAY: return "MIERCOLES";
            case THURSDAY: return "JUEVES";
            case FRIDAY: return "VIERNES";
            case SATURDAY: return "SABADO";
            case SUNDAY: return "DOMINGO";
            default: return null;
        }
    }
}
