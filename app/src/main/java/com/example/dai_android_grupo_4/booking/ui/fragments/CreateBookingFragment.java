package com.example.dai_android_grupo_4.booking.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.booking.ui.viewmodel.BookingViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateBookingFragment extends Fragment {

    private Spinner spinnerClass, spinnerInstructor, spinnerDate, spinnerTime;
    private TextView tvSelectedClass, tvSelectedInstructor, tvSelectedDate, tvSelectedTime;
    private Button btnCreateBooking;
    private BookingViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupSpinners();
        setupClickListeners();
    }

    private void initViews(View view) {
        spinnerClass = view.findViewById(R.id.spinner_class);
        spinnerInstructor = view.findViewById(R.id.spinner_instructor);
        spinnerDate = view.findViewById(R.id.spinner_date);
        spinnerTime = view.findViewById(R.id.spinner_time);
        
        tvSelectedClass = view.findViewById(R.id.tv_selected_class);
        tvSelectedInstructor = view.findViewById(R.id.tv_selected_instructor);
        tvSelectedDate = view.findViewById(R.id.tv_selected_date);
        tvSelectedTime = view.findViewById(R.id.tv_selected_time);
        
        btnCreateBooking = view.findViewById(R.id.btn_create_booking);
    }

    private void setupSpinners() {
        // Configurar spinner de clases
        List<String> classes = new ArrayList<>();
        classes.add("Yoga Matutino");
        classes.add("Pilates Avanzado");
        classes.add("Spinning");
        classes.add("CrossFit");
        classes.add("Zumba");
        
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_spinner_item, classes);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(classAdapter);

        // Configurar spinner de instructores
        List<String> instructors = new ArrayList<>();
        instructors.add("María González");
        instructors.add("Carlos Ruiz");
        instructors.add("Ana Martínez");
        instructors.add("Roberto Silva");
        instructors.add("Laura Fernández");
        
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_spinner_item, instructors);
        instructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInstructor.setAdapter(instructorAdapter);

        // Configurar spinner de fechas
        List<String> dates = new ArrayList<>();
        dates.add("15 de Marzo, 2024");
        dates.add("16 de Marzo, 2024");
        dates.add("17 de Marzo, 2024");
        dates.add("18 de Marzo, 2024");
        dates.add("19 de Marzo, 2024");
        
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_spinner_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(dateAdapter);

        // Configurar spinner de horarios
        List<String> times = new ArrayList<>();
        times.add("07:00 - 08:00");
        times.add("09:00 - 10:00");
        times.add("18:00 - 19:00");
        times.add("19:00 - 20:00");
        times.add("20:00 - 21:00");
        
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);

        // Configurar listeners para actualizar los TextViews
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvSelectedClass.setText(classes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerInstructor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvSelectedInstructor.setText(instructors.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvSelectedDate.setText(dates.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvSelectedTime.setText(times.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupClickListeners() {
        btnCreateBooking.setOnClickListener(v -> {
            createBooking();
        });
    }

    private void createBooking() {
        // Crear objeto Booking con los datos seleccionados
        Booking booking = new Booking();
        booking.setClassName(tvSelectedClass.getText().toString());
        booking.setInstructor(tvSelectedInstructor.getText().toString());
        booking.setDate(tvSelectedDate.getText().toString());
        booking.setTime(tvSelectedTime.getText().toString());
        booking.setLocation("RitmoFit Centro"); // Por defecto
        booking.setDuration("60 min");
        booking.setCapacity(20);
        booking.setCurrentBookings(0);
        booking.setDescription("Reserva creada desde la app");

        // Aquí se integraría con el ViewModel para crear la reserva
        Toast.makeText(getContext(), "Reserva creada exitosamente", Toast.LENGTH_SHORT).show();
        
        // Navegar de vuelta a la lista de reservas
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}
