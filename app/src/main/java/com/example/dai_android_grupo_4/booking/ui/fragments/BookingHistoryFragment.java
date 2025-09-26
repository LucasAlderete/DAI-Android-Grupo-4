package com.example.dai_android_grupo_4.booking.ui.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.historial.model.AsistenciaResponse;
import com.example.dai_android_grupo_4.historial.repository.HistorialRepository;
import com.example.dai_android_grupo_4.historial.ui.HistorialAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingHistoryFragment extends Fragment {

    private static final String TAG = "BookingHistoryFragment";

    @Inject
    HistorialRepository historialRepository;

    private RecyclerView recyclerView;
    private HistorialAdapter adapter;
    private TextView titleText;

    // Filtros UI
    private LinearLayout filterHeader;
    private LinearLayout filterContent;
    private TextView filterArrow;
    private TextInputEditText etFechaInicio;
    private TextInputEditText etFechaFin;
    private MaterialButton btnAplicarFiltros;
    private MaterialButton btnLimpiarFiltros;

    // Estados del filtro
    private boolean isFilterExpanded = false;
    private String fechaInicioSeleccionada = null;
    private String fechaFinSeleccionada = null;
    private boolean isLoadingData = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupRecyclerView();
        setupFilterListeners();
        loadHistorial();
    }

    private void initViews(View view) {
        // Views existentes
        titleText = view.findViewById(R.id.tv_historial_title);
        recyclerView = view.findViewById(R.id.rv_historial_list);

        // Views de filtros
        filterHeader = view.findViewById(R.id.layout_filter_header);
        filterContent = view.findViewById(R.id.layout_filter_content);
        filterArrow = view.findViewById(R.id.tv_filter_arrow);
        etFechaInicio = view.findViewById(R.id.et_fecha_inicio);
        etFechaFin = view.findViewById(R.id.et_fecha_fin);
        btnAplicarFiltros = view.findViewById(R.id.btn_aplicar_filtros);
        btnLimpiarFiltros = view.findViewById(R.id.btn_limpiar_filtros);

        titleText.setText("Historial de Asistencias");
    }

    private void setupRecyclerView() {
        adapter = new HistorialAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupFilterListeners() {
        // Toggle del filtro (expand/collapse)
        filterHeader.setOnClickListener(v -> toggleFilterExpansion());

        // DatePickers
        etFechaInicio.setOnClickListener(v -> showDatePicker(true));
        etFechaFin.setOnClickListener(v -> showDatePicker(false));

        // Botones
        btnAplicarFiltros.setOnClickListener(v -> aplicarFiltros());
        btnLimpiarFiltros.setOnClickListener(v -> limpiarFiltros());
    }

    private void toggleFilterExpansion() {
        isFilterExpanded = !isFilterExpanded;

        if (isFilterExpanded) {
            filterContent.setVisibility(View.VISIBLE);
            filterArrow.setText("▲");
        } else {
            filterContent.setVisibility(View.GONE);
            filterArrow.setText("▼");
        }
    }

    private void showDatePicker(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
            getContext(),
            (view, year, month, dayOfMonth) -> {
                String fechaSeleccionada = String.format(Locale.getDefault(), "%04d-%02d-%02dT00:00:00", year, month + 1, dayOfMonth);
                String fechaMostrar = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);

                if (isStartDate) {
                    fechaInicioSeleccionada = fechaSeleccionada;
                    etFechaInicio.setText(fechaMostrar);
                } else {
                    fechaFinSeleccionada = fechaSeleccionada;
                    etFechaFin.setText(fechaMostrar);
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void aplicarFiltros() {
        if (fechaInicioSeleccionada != null && fechaFinSeleccionada != null) {
            Log.d(TAG, "Aplicando filtros - Inicio: " + fechaInicioSeleccionada + ", Fin: " + fechaFinSeleccionada);
            loadHistorialConFiltros(fechaInicioSeleccionada, fechaFinSeleccionada);
        } else {
            Toast.makeText(getContext(), "Selecciona ambas fechas", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarFiltros() {
        fechaInicioSeleccionada = null;
        fechaFinSeleccionada = null;
        etFechaInicio.setText("");
        etFechaFin.setText("");
        loadHistorial(); // Cargar todos los datos sin filtros
    }

    private void loadHistorial() {
        historialRepository.getHistorialAsistencias(0, 10, new HistorialRepository.HistorialCallback() {
            @Override
            public void onSuccess(List<AsistenciaResponse> asistencias) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter.setAsistencias(asistencias);
                        titleText.setText("Historial (" + asistencias.size() + " asistencias)");
                    });
                }
            }

            @Override
            public void onError(String error) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private void loadHistorialConFiltros(String fechaInicio, String fechaFin) {
        Log.d(TAG, "Llamando API con filtros...");
        historialRepository.getHistorialAsistenciasConFiltros(0, 10, fechaInicio, fechaFin, new HistorialRepository.HistorialCallback() {
            @Override
            public void onSuccess(List<AsistenciaResponse> asistencias) {
                Log.d(TAG, "API respuesta exitosa con " + (asistencias != null ? asistencias.size() : 0) + " asistencias");
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (asistencias != null) {
                            adapter.setAsistencias(asistencias);
                            titleText.setText("Filtrado: " + asistencias.size() + " asistencias");
                            Log.d(TAG, "Adapter actualizado con " + asistencias.size() + " elementos");
                        } else {
                            Log.w(TAG, "Lista de asistencias es null");
                            adapter.setAsistencias(new ArrayList<>());
                            titleText.setText("Filtrado: 0 asistencias");
                        }
                    });
                }
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, "Error en API filtrada: " + error);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Error en filtros: " + error, Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }
}