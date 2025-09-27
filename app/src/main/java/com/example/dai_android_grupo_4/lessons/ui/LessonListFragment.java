package com.example.dai_android_grupo_4.lessons.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.lessons.model.Discipline;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.model.Site;
import com.example.dai_android_grupo_4.lessons.ui.adapter.LessonAdapter;
import com.example.dai_android_grupo_4.lessons.ui.viewmodels.LessonViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LessonListFragment extends Fragment {

    private LessonViewModel viewModel;
    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private Spinner spinnerSite, spinnerDiscipline;
    private TextView tvFechaFilter;

    private Site selectedSite = null;
    private Discipline selectedDiscipline = null;
    private String selectedDate = null;
    private boolean isSiteSpinnerInitialized = false;
    private boolean isDisciplineSpinnerInitialized = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LessonViewModel.class);

        initViews(view);
        setupRecyclerView();
        setupFilters();
        observeViewModel();

        // Carga inicial de datos
        viewModel.fetchLessons(null, null, null);
        viewModel.fetchSites();
        viewModel.fetchDisciplines();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewClases);
        spinnerSite = view.findViewById(R.id.spinner_sede);
        spinnerDiscipline = view.findViewById(R.id.spinner_disciplina);
        tvFechaFilter = view.findViewById(R.id.tv_fecha_filter);
    }

    private void setupRecyclerView() {
        adapter = new LessonAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupFilters() {
        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isSiteSpinnerInitialized) {
                    isSiteSpinnerInitialized = true;
                    return;
                }
                selectedSite = (position == 0) ? null : (Site) parent.getItemAtPosition(position);
                triggerFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerDiscipline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isDisciplineSpinnerInitialized) {
                    isDisciplineSpinnerInitialized = true;
                    return;
                }
                selectedDiscipline = (position == 0) ? null : (Discipline) parent.getItemAtPosition(position);
                triggerFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        tvFechaFilter.setOnClickListener(v -> showDatePickerDialog());
    }

    private void observeViewModel() {
        viewModel.getLessons().observe(getViewLifecycleOwner(), clases -> {
            if (selectedDate == null || selectedDate.isEmpty()) {
                adapter.setLessons(clases);
            } else {
                List<Lesson> filteredLessons = new ArrayList<>();
                for (Lesson lesson : clases) {
                    if (lesson.getFechaInicio() != null && lesson.getFechaInicio().startsWith(selectedDate)) {
                        filteredLessons.add(lesson);
                    }
                }
                adapter.setLessons(filteredLessons);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Handle loading state
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getSites().observe(getViewLifecycleOwner(), this::updateSitesSpinner);
        viewModel.getDisciplines().observe(getViewLifecycleOwner(), this::updateDisciplinesSpinner);
    }

    private void updateSitesSpinner(List<Site> sites) {
        List<Object> siteObjects = new ArrayList<>();
        siteObjects.add("Todas las sedes");
        siteObjects.addAll(sites);
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, siteObjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSite.setAdapter(adapter);
    }

    private void updateDisciplinesSpinner(List<Discipline> disciplines) {
        List<Object> disciplineObjects = new ArrayList<>();
        disciplineObjects.add("Todas las disciplinas");
        disciplineObjects.addAll(disciplines);
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, disciplineObjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiscipline.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    selectedDate = String.format("%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
                    tvFechaFilter.setText(selectedDate);
                    triggerFilter();
                }, year, month, day);

        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEUTRAL, "Limpiar", (dialog, which) -> {
            selectedDate = null;
            tvFechaFilter.setText("Fecha");
            triggerFilter();
        });

        datePickerDialog.show();
    }

    private void triggerFilter() {
        Long siteId = (selectedSite != null) ? selectedSite.getId() : null;
        Long disciplineId = (selectedDiscipline != null) ? selectedDiscipline.getId() : null;
        // Fetch all and filter in observer
        viewModel.fetchLessons(siteId, disciplineId, null);
    }
}
