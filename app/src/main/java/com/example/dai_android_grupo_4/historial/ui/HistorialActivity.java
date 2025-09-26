package com.example.dai_android_grupo_4.historial.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.historial.model.AsistenciaResponse;
import com.example.dai_android_grupo_4.historial.repository.HistorialRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistorialActivity extends AppCompatActivity {

    @Inject
    HistorialRepository historialRepository;

    private RecyclerView recyclerView;
    private HistorialAdapter adapter;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        initViews();
        setupRecyclerView();
        loadHistorial();
    }

    private void initViews() {
        titleText = findViewById(R.id.tv_historial_title);
        recyclerView = findViewById(R.id.rv_historial_list);
        titleText.setText("Historial de Asistencias");
    }

    private void setupRecyclerView() {
        adapter = new HistorialAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadHistorial() {
        historialRepository.getHistorialAsistencias(0, 10, new HistorialRepository.HistorialCallback() {
            @Override
            public void onSuccess(List<AsistenciaResponse> asistencias) {
                runOnUiThread(() -> {
                    adapter.setAsistencias(asistencias);
                    titleText.setText("Historial (" + asistencias.size() + " asistencias)");
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(HistorialActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}