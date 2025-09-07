package com.example.dai_android_grupo_4.activities.clases;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.adapter.clases.ClasesAdapter;
import com.example.dai_android_grupo_4.model.Disciplina;
import com.example.dai_android_grupo_4.model.Sede;
import com.example.dai_android_grupo_4.viewmodels.ClasesViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClasesActivity extends AppCompatActivity {

    private ClasesViewModel viewModel;
    private RecyclerView recyclerView;
    private ClasesAdapter adapter;
    private Spinner spinnerSede, spinnerDisciplina;
    private TextView tvFechaFilter;

    private Sede selectedSede = null;
    private Disciplina selectedDisciplina = null;
    private String selectedDate = null;

    // Flags para evitar la llamada inicial de los listeners de los Spinners
    private boolean isSedeSpinnerInitialized = false;
    private boolean isDisciplinaSpinnerInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clases);

        viewModel = new ViewModelProvider(this).get(ClasesViewModel.class);

        setupHeader();
        initViews();
        setupRecyclerView();
        setupFilters();
        observeViewModel();

        // Carga inicial de datos
        viewModel.fetchClases(null, null, null);
    }

    private void setupHeader() {
        ImageView backArrow = findViewById(R.id.iv_back_arrow);
        TextView headerTitle = findViewById(R.id.tv_header_title);
        headerTitle.setText("Clases");
        backArrow.setOnClickListener(v -> finish());
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewClases);
        spinnerSede = findViewById(R.id.spinner_sede);
        spinnerDisciplina = findViewById(R.id.spinner_disciplina);
        tvFechaFilter = findViewById(R.id.tv_fecha_filter);
    }

    private void setupRecyclerView() {
        adapter = new ClasesAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupFilters() {
        spinnerSede.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isSedeSpinnerInitialized) {
                    isSedeSpinnerInitialized = true;
                    return;
                }
                selectedSede = (position == 0) ? null : (Sede) parent.getItemAtPosition(position);
                triggerFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerDisciplina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isDisciplinaSpinnerInitialized) {
                    isDisciplinaSpinnerInitialized = true;
                    return;
                }
                selectedDisciplina = (position == 0) ? null : (Disciplina) parent.getItemAtPosition(position);
                triggerFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        tvFechaFilter.setOnClickListener(v -> showDatePickerDialog());
    }

    private void observeViewModel() {
        viewModel.getClases().observe(this, clases -> adapter.setClases(clases));

        viewModel.getIsLoading().observe(this, isLoading -> {
            // Aquí se puede manejar un ProgressBar
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getSedesOptions().observe(this, sedes -> {
            List<Sede> sedeOptions = new ArrayList<>(sedes);
            sedeOptions.add(0, new Sede());
            ArrayAdapter<Sede> sedeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sedeOptions);
            sedeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSede.setAdapter(sedeAdapter);
            isSedeSpinnerInitialized = false;
        });

        viewModel.getDisciplinasOptions().observe(this, disciplinas -> {
            List<Disciplina> disciplinaOptions = new ArrayList<>(disciplinas);
            disciplinaOptions.add(0, new Disciplina());
            ArrayAdapter<Disciplina> disciplinaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, disciplinaOptions);
            disciplinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDisciplina.setAdapter(disciplinaAdapter);
            isDisciplinaSpinnerInitialized = false;
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                    tvFechaFilter.setText(selectedDate);
                    triggerFilter();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void triggerFilter() {
        Long sedeId = (selectedSede != null && selectedSede.getId() != null) ? selectedSede.getId() : null;
        Long disciplinaId = (selectedDisciplina != null && selectedDisciplina.getId() != null) ? selectedDisciplina.getId() : null;
        viewModel.fetchClases(sedeId, disciplinaId, selectedDate);
    }
}