package com.example.dai_android_grupo_4.preferences.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.lessons.model.Discipline;
import com.example.dai_android_grupo_4.lessons.model.Site;
import com.example.dai_android_grupo_4.lessons.ui.viewmodels.LessonViewModel;
import com.example.dai_android_grupo_4.preferences.model.DisciplinaFavorita;
import com.example.dai_android_grupo_4.preferences.model.DiaSemana;
import com.example.dai_android_grupo_4.preferences.model.DisponibilidadHoraria;
import com.example.dai_android_grupo_4.preferences.model.SedeFavorita;
import com.example.dai_android_grupo_4.preferences.ui.adapter.DisciplinaFavoritaAdapter;
import com.example.dai_android_grupo_4.preferences.ui.adapter.DisponibilidadHorariaAdapter;
import com.example.dai_android_grupo_4.preferences.ui.adapter.SedeFavoritaAdapter;
import com.example.dai_android_grupo_4.preferences.ui.viewmodel.PreferencesViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PreferencesFragment extends Fragment {

    private PreferencesViewModel preferencesViewModel;
    private LessonViewModel lessonViewModel;

    private Spinner spinnerDisciplinas;
    private Spinner spinnerSedes;
    private MaterialButton btnAgregarDisciplina;
    private MaterialButton btnAgregarSede;
    private RecyclerView recyclerDisciplinasFavoritas;
    private RecyclerView recyclerSedesFavoritas;

    private DisciplinaFavoritaAdapter disciplinaFavoritaAdapter;
    private SedeFavoritaAdapter sedeFavoritaAdapter;

    private List<Discipline> allDisciplines = new ArrayList<>();
    private List<Site> allSites = new ArrayList<>();

    private Spinner spinnerDiaSemana;
    private MaterialButton btnHoraInicio;
    private MaterialButton btnHoraFin;
    private MaterialButton btnAgregarDisponibilidad;
    private RecyclerView recyclerDisponibilidades;
    private DisponibilidadHorariaAdapter disponibilidadAdapter;

    private String horaInicioSeleccionada = null;
    private String horaFinSeleccionada = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preferences, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferencesViewModel = new ViewModelProvider(this).get(PreferencesViewModel.class);
        lessonViewModel = new ViewModelProvider(this).get(LessonViewModel.class);

        initViews(view);
        setupRecyclerViews();
        setupListeners();
        observeViewModels();

        lessonViewModel.fetchDisciplines();
        lessonViewModel.fetchSites();
        preferencesViewModel.fetchDisciplinasFavoritas();
        preferencesViewModel.fetchSedesFavoritas();

        updateDiasSpinner();
        preferencesViewModel.fetchDisponibilidades();
    }

    private void initViews(View view) {
        spinnerDisciplinas = view.findViewById(R.id.spinnerDisciplinas);
        spinnerSedes = view.findViewById(R.id.spinnerSedes);
        btnAgregarDisciplina = view.findViewById(R.id.btnAgregarDisciplina);
        btnAgregarSede = view.findViewById(R.id.btnAgregarSede);
        recyclerDisciplinasFavoritas = view.findViewById(R.id.recyclerDisciplinasFavoritas);
        recyclerSedesFavoritas = view.findViewById(R.id.recyclerSedesFavoritas);

        spinnerDiaSemana = view.findViewById(R.id.spinnerDiaSemana);
        btnHoraInicio = view.findViewById(R.id.btnHoraInicio);
        btnHoraFin = view.findViewById(R.id.btnHoraFin);
        btnAgregarDisponibilidad = view.findViewById(R.id.btnAgregarDisponibilidad);
        recyclerDisponibilidades = view.findViewById(R.id.recyclerDisponibilidades);
    }

    private void setupRecyclerViews() {
        disciplinaFavoritaAdapter = new DisciplinaFavoritaAdapter(disciplinaFavorita -> {
            preferencesViewModel.removeDisciplinaFavorita(disciplinaFavorita.getId());
        });
        recyclerDisciplinasFavoritas.setAdapter(disciplinaFavoritaAdapter);
        recyclerDisciplinasFavoritas.setLayoutManager(new LinearLayoutManager(requireContext()));

        sedeFavoritaAdapter = new SedeFavoritaAdapter(sedeFavorita -> {
            preferencesViewModel.removeSedeFavorita(sedeFavorita.getId());
        });
        recyclerSedesFavoritas.setAdapter(sedeFavoritaAdapter);
        recyclerSedesFavoritas.setLayoutManager(new LinearLayoutManager(requireContext()));

        disponibilidadAdapter = new DisponibilidadHorariaAdapter(disponibilidad -> {
            preferencesViewModel.removeDisponibilidad(disponibilidad.getId());
        });
        recyclerDisponibilidades.setAdapter(disponibilidadAdapter);
        recyclerDisponibilidades.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupListeners() {
        btnAgregarDisciplina.setOnClickListener(v -> {
            int position = spinnerDisciplinas.getSelectedItemPosition();
            if (position > 0 && !allDisciplines.isEmpty()) {
                Discipline selected = allDisciplines.get(position - 1);
                preferencesViewModel.addDisciplinaFavorita(selected.getId());
            } else {
                Toast.makeText(requireContext(), "Selecciona una disciplina", Toast.LENGTH_SHORT).show();
            }
        });

        btnAgregarSede.setOnClickListener(v -> {
            int position = spinnerSedes.getSelectedItemPosition();
            if (position > 0 && !allSites.isEmpty()) {
                Site selected = allSites.get(position - 1);
                preferencesViewModel.addSedeFavorita(selected.getId());
            } else {
                Toast.makeText(requireContext(), "Selecciona una sede", Toast.LENGTH_SHORT).show();
            }
        });

        btnHoraInicio.setOnClickListener(v -> showTimePickerDialog(true));
        btnHoraFin.setOnClickListener(v -> showTimePickerDialog(false));

        btnAgregarDisponibilidad.setOnClickListener(v -> {
            int diaPosition = spinnerDiaSemana.getSelectedItemPosition();

            if (diaPosition > 0 && horaInicioSeleccionada != null && horaFinSeleccionada != null) {
                DiaSemana diaSeleccionado = DiaSemana.values()[diaPosition - 1];

                DisponibilidadHoraria nuevaDisponibilidad = new DisponibilidadHoraria(
                        diaSeleccionado,
                        horaInicioSeleccionada,
                        horaFinSeleccionada
                );

                preferencesViewModel.addDisponibilidad(nuevaDisponibilidad);

                horaInicioSeleccionada = null;
                horaFinSeleccionada = null;
                btnHoraInicio.setText("Hora Inicio");
                btnHoraFin.setText("Hora Fin");
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeViewModels() {
        lessonViewModel.getDisciplines().observe(getViewLifecycleOwner(), disciplines -> {
            if (disciplines != null) {
                allDisciplines = disciplines;
                updateDisciplinesSpinner(disciplines);
            }
        });

        lessonViewModel.getSites().observe(getViewLifecycleOwner(), sites -> {
            if (sites != null) {
                allSites = sites;
                updateSitesSpinner(sites);
            }
        });

        preferencesViewModel.getDisciplinasFavoritas().observe(getViewLifecycleOwner(), disciplinasFavoritas -> {
            if (disciplinasFavoritas != null) {
                disciplinaFavoritaAdapter.setItems(disciplinasFavoritas);
            }
        });

        preferencesViewModel.getSedesFavoritas().observe(getViewLifecycleOwner(), sedesFavoritas -> {
            if (sedesFavoritas != null) {
                sedeFavoritaAdapter.setItems(sedesFavoritas);
            }
        });

        preferencesViewModel.getDisponibilidades().observe(getViewLifecycleOwner(), disponibilidades -> {
            if (disponibilidades != null) {
                disponibilidadAdapter.setItems(disponibilidades);
            }
        });

        preferencesViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        preferencesViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDisciplinesSpinner(List<Discipline> disciplines) {
        List<String> disciplineNames = new ArrayList<>();
        disciplineNames.add("Selecciona una disciplina");
        for (Discipline discipline : disciplines) {
            disciplineNames.add(discipline.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, disciplineNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisciplinas.setAdapter(adapter);
    }

    private void updateSitesSpinner(List<Site> sites) {
        List<String> siteNames = new ArrayList<>();
        siteNames.add("Selecciona una sede");
        for (Site site : sites) {
            siteNames.add(site.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, siteNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSedes.setAdapter(adapter);
    }

    private void updateDiasSpinner() {
        List<String> diasNombres = new ArrayList<>();
        diasNombres.add("Selecciona un día");

        for (DiaSemana dia : DiaSemana.values()) {
            diasNombres.add(dia.toSpanish());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                diasNombres
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiaSemana.setAdapter(adapter);
    }

    private void showTimePickerDialog(boolean esHoraInicio) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendar.get(java.util.Calendar.MINUTE);

        android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(
                requireContext(),
                (view, selectedHour, selectedMinute) -> {
                    String horaFormateada = String.format("%02d:%02d:00", selectedHour, selectedMinute);

                    if (esHoraInicio) {
                        horaInicioSeleccionada = horaFormateada;
                        btnHoraInicio.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    } else {
                        horaFinSeleccionada = horaFormateada;
                        btnHoraFin.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }
}
