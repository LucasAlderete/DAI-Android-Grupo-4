package com.example.dai_android_grupo_4.activities.clases;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.adapter.clases.ClasesAdapter;
import com.example.dai_android_grupo_4.data.api.model.clases.ClaseListResponse;
import com.example.dai_android_grupo_4.data.api.repository.clases.ClaseServiceCallBack;
import com.example.dai_android_grupo_4.services.clases.ClaseService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClasesActivity extends AppCompatActivity {

    private static final String TAG = "ClasesActivity";

    @Inject
    ClaseService claseService;

    private RecyclerView recyclerView;
    private ClasesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "⭐ onCreate: La Activity está siendo creada");
        setContentView(R.layout.activity_clases);

        setupRecyclerView();
        fetchClases();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "⭐ onStart: La Activity está a punto de hacerse visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "⭐ onResume: La Activity es visible y tiene el foco");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "⭐ onPause: La Activity está perdiendo el foco");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "⭐ onStop: La Activity ya no es visible");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "⭐ onRestart: La Activity está volviendo a empezar después de detenerse");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "⭐ onDestroy: La Activity está siendo destruida");
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewClases);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClasesAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void fetchClases() {
        // Llamamos al servicio para obtener la primera página de clases
        claseService.getAllClases(0, 20, null, null, null, new ClaseServiceCallBack() {
            @Override
            public void onSuccess(ClaseListResponse claseListResponse) {
                // Usamos el método toClases() que agregamos previamente
                adapter.setClases(claseListResponse.toClases());
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Fallo en la llamada a la API de clases", error);
            }
        });
    }
}
