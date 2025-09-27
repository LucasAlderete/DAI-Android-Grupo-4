package com.example.dai_android_grupo_4.lessons.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.lessons.model.Lesson;
import com.example.dai_android_grupo_4.lessons.ui.viewmodels.LessonDetailViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LessonDetailFragment extends Fragment {

    public static final String ARG_CLASE_ID = "ARG_CLASE_ID";

    private LessonDetailViewModel viewModel;
    private TextView tvLessonName, tvDateTime, tvDuration, tvInstructor, tvSpots, tvLocation, tvAddress;
    private Button btnBook;

    public static LessonDetailFragment newInstance(long claseId) {
        LessonDetailFragment fragment = new LessonDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CLASE_ID, claseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LessonDetailViewModel.class);

        initViews(view);

        if (getArguments() != null) {
            long claseId = getArguments().getLong(ARG_CLASE_ID, -1);
            if (claseId != -1) {
                viewModel.fetchLessonDetails(claseId);
            }
        }

        observeViewModel();
    }

    private void initViews(View view) {
        tvLessonName = view.findViewById(R.id.tv_lesson_name);
        tvDateTime = view.findViewById(R.id.tv_date_time);
        tvDuration = view.findViewById(R.id.tv_duration);
        tvInstructor = view.findViewById(R.id.tv_instructor);
        tvSpots = view.findViewById(R.id.tv_spots);
        tvLocation = view.findViewById(R.id.tv_location);
        tvAddress = view.findViewById(R.id.tv_address);
        btnBook = view.findViewById(R.id.btn_book);
    }

    private void observeViewModel() {
        viewModel.getLessonDetail().observe(getViewLifecycleOwner(), this::updateUi);
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Handle loading state
        });
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getBookingResult().observe(getViewLifecycleOwner(), booking -> {
            if (booking != null) {
                Toast.makeText(requireContext(), "Reserva creada con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getBookingError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error al crear la reserva: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUi(Lesson lesson) {
        if (lesson == null) return;

        tvLessonName.setText(lesson.getDisciplina().getNombre());
        tvLocation.setText(lesson.getSede().getNombre());
        tvAddress.setText(lesson.getSede().getDireccion());
        tvInstructor.setText(lesson.getInstructor().getNombre() + " " + lesson.getInstructor().getApellido());
        tvSpots.setText(String.valueOf(lesson.getCupoMaximo() - lesson.getCupoActual()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        try {
            Date startDate = sdf.parse(lesson.getFechaInicio());
            Date endDate = sdf.parse(lesson.getFechaFin());
            if (startDate != null && endDate != null) {
                long diffInMillis = endDate.getTime() - startDate.getTime();
                long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
                tvDuration.setText(durationInMinutes + " minutos");

                SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                tvDateTime.setText(timeFormat.format(startDate));
            }
        } catch (ParseException e) {
            tvDateTime.setText("Fecha no disponible");
            tvDuration.setText("Duración no disponible");
        }

        btnBook.setEnabled(lesson.isDisponible());
        btnBook.setOnClickListener(v -> {
            long claseId = getArguments().getLong(ARG_CLASE_ID, -1);
            if (claseId != -1) {
                viewModel.createBooking(claseId, ""); // Hardcoded token as requested
            }
        });
    }
}
