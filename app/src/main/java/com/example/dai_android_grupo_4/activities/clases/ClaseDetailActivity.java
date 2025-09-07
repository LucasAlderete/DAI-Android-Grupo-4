package com.example.dai_android_grupo_4.activities.clases;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.model.ClaseDetail;
import com.example.dai_android_grupo_4.viewmodels.ClaseDetailViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClaseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CLASE_ID = "EXTRA_CLASE_ID";

    private ClaseDetailViewModel viewModel;
    private TextView tvDisciplina, tvFechaHora, tvDuracion, tvInstructor, tvCupos, tvSede, tvDireccion;
    private Button btnReservar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clase_detail);

        viewModel = new ViewModelProvider(this).get(ClaseDetailViewModel.class);

        setupHeader();
        initViews();

        long claseId = getIntent().getLongExtra(EXTRA_CLASE_ID, -1);
        if (claseId != -1) {
            viewModel.fetchClaseDetails(claseId);
        }

        observeViewModel();
    }

    private void setupHeader() {
        ImageView backArrow = findViewById(R.id.iv_back_arrow);
        TextView headerTitle = findViewById(R.id.tv_header_title);

        headerTitle.setText("Detalle");
        backArrow.setOnClickListener(v -> {
            finish();
        });
    }

    private void initViews() {
        tvDisciplina = findViewById(R.id.tvDetailDisciplina);
        tvFechaHora = findViewById(R.id.tvDetailFechaHora);
        tvDuracion = findViewById(R.id.tvDetailDuracion);
        tvInstructor = findViewById(R.id.tvDetailInstructor);
        tvCupos = findViewById(R.id.tvDetailCupos);
        tvSede = findViewById(R.id.tvDetailSede);
        tvDireccion = findViewById(R.id.tvDetailDireccion);
        btnReservar = findViewById(R.id.btnReservar);
    }

    private void observeViewModel() {
        viewModel.getClaseDetail().observe(this, this::updateUi);
        viewModel.getIsLoading().observe(this, isLoading -> {
            // Aquí podrías mostrar un ProgressBar
        });
        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUi(ClaseDetail clase) {
        if (clase == null) return;

        tvDisciplina.setText(clase.getDisciplina().getNombre());
        tvFechaHora.setText(formatFechaHora(clase.getFechaHora()));
        tvDuracion.setText("Duración: " + clase.getDuracionMinutos() + " minutos");
        tvInstructor.setText("Instructor: " + clase.getNombreInstructor());
        tvCupos.setText("Cupos disponibles: " + clase.getCupoDisponible());
        tvSede.setText("Sede: " + clase.getSede().getNombre());
        tvDireccion.setText("Ubicación: " + clase.getSede().getDireccion());

        btnReservar.setOnClickListener(v -> {
            // Lógica para reservar la clase
            Toast.makeText(this, "Reserva solicitada para " + clase.getDisciplina().getNombre(), Toast.LENGTH_SHORT).show();
        });
    }

    private String formatFechaHora(String fechaHoraStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("E d 'de' MMMM HH:mm'hs'", new Locale("es", "ES"));

        try {
            Date date = inputFormat.parse(fechaHoraStr);
            if (date != null) {
                String formattedDate = outputFormat.format(date);
                formattedDate = formattedDate.replace(".", "");
                return formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaHoraStr;
    }
}
