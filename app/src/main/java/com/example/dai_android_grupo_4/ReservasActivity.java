package com.example.dai_android_grupo_4;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dai_android_grupo_4.data.api.repository.ReservaServiceCallback;
import com.example.dai_android_grupo_4.model.Reserva;
import com.example.dai_android_grupo_4.services.ReservaService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReservasActivity extends AppCompatActivity {


    private static final String TAG = "ActivityLifecycle";

    @Inject
    ReservaService reservaService;

    private ListView listView;
    private List<String> reservaDisplayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "⭐ onCreate: La Activity está siendo creada");
        setContentView(R.layout.activity_reservas);

        listView = findViewById(R.id.listView);
        reservaDisplayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                reservaDisplayList);
        listView.setAdapter(adapter);
        loadReservas();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedReserva = reservaDisplayList.get(position);
            String pokemonName = selectedReserva.split(" - ")[0];

            //Intent intent = new Intent(MainActivity.this, PokemonDetailActivity.class);
            //intent.putExtra(PokemonDetailActivity.EXTRA_POKEMON_NAME, pokemonName);
            //startActivity(intent);
        });
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

    private void loadReservas() {
        reservaService.getAllReservas(new ReservaServiceCallback() {
            @Override
            public void onSuccess(List<Reserva> reservas) {
                reservaDisplayList.clear();
                reservaDisplayList.addAll(reservas.stream()
                        .map(reserva -> reserva.getClase() + " (" + reserva.getHorario() + ") - " + reserva.getProfesor())
                        .collect(Collectors.toList()));
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            }

            @Override
            public void onError(Throwable error) {
                runOnUiThread(() -> Toast.makeText(ReservasActivity.this,
                        "Error al cargar las reservas: " + error.getMessage(),
                        Toast.LENGTH_LONG).show());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "⭐ onSaveInstanceState: Guardando el estado de la Activity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "⭐ onRestoreInstanceState: Restaurando el estado guardado de la Activity");
    }
}