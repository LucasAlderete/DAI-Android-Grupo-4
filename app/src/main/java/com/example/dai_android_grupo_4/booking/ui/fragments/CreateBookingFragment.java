package com.example.dai_android_grupo_4.booking.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import android.widget.ArrayAdapter;
import com.google.android.material.button.MaterialButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.booking.ui.viewmodel.BookingViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateBookingFragment extends Fragment {

    private AutoCompleteTextView spinnerClass, spinnerInstructor, spinnerDate, spinnerTime;
    private TextView tvSelectedClass, tvSelectedInstructor, tvSelectedDate, tvSelectedTime;
    private MaterialButton btnCreateBooking;
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
        initViewModel();
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

    private void initViewModel() {
        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity()).get(BookingViewModel.class);
        }
    }

    private void setupSpinners() {
        // Configurar AutoCompleteTextView de clases
        List<String> classes = new ArrayList<>();
        classes.add("Yoga Matutino");
        classes.add("Pilates Avanzado");
        classes.add("Spinning");
        classes.add("CrossFit");
        classes.add("Zumba");
        
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, classes);
        spinnerClass.setAdapter(classAdapter);
        spinnerClass.setText(classes.get(0), false); // Establecer valor por defecto
        tvSelectedClass.setText(classes.get(0));

        // Configurar AutoCompleteTextView de instructores
        List<String> instructors = new ArrayList<>();
        instructors.add("María González");
        instructors.add("Carlos Ruiz");
        instructors.add("Ana Martínez");
        instructors.add("Roberto Silva");
        instructors.add("Laura Fernández");
        
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, instructors);
        spinnerInstructor.setAdapter(instructorAdapter);
        spinnerInstructor.setText(instructors.get(0), false); // Establecer valor por defecto
        tvSelectedInstructor.setText(instructors.get(0));

        // Configurar AutoCompleteTextView de fechas
        List<String> dates = new ArrayList<>();
        dates.add("15 de Marzo, 2024");
        dates.add("16 de Marzo, 2024");
        dates.add("17 de Marzo, 2024");
        dates.add("18 de Marzo, 2024");
        dates.add("19 de Marzo, 2024");
        
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, dates);
        spinnerDate.setAdapter(dateAdapter);
        spinnerDate.setText(dates.get(0), false); // Establecer valor por defecto
        tvSelectedDate.setText(dates.get(0));

        // Configurar AutoCompleteTextView de horarios
        List<String> times = new ArrayList<>();
        times.add("07:00 - 08:00");
        times.add("09:00 - 10:00");
        times.add("18:00 - 19:00");
        times.add("19:00 - 20:00");
        times.add("20:00 - 21:00");
        
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, times);
        spinnerTime.setAdapter(timeAdapter);
        spinnerTime.setText(times.get(1), false); // Establecer valor por defecto
        tvSelectedTime.setText(times.get(1));

        // Configurar listeners para actualizar los TextViews
        spinnerClass.setOnItemClickListener((parent, view, position, id) -> {
            String selectedClass = classes.get(position);
            tvSelectedClass.setText(selectedClass);
        });

        spinnerInstructor.setOnItemClickListener((parent, view, position, id) -> {
            String selectedInstructor = instructors.get(position);
            tvSelectedInstructor.setText(selectedInstructor);
        });

        spinnerDate.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDate = dates.get(position);
            tvSelectedDate.setText(selectedDate);
        });

        spinnerTime.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTime = times.get(position);
            tvSelectedTime.setText(selectedTime);
        });
    }

    private void setupClickListeners() {
        btnCreateBooking.setOnClickListener(v -> {
            createBooking();
        });
    }

    private void createBooking() {
        // Validar que todos los campos estén seleccionados
        if (spinnerClass.getText().toString().isEmpty() ||
            spinnerInstructor.getText().toString().isEmpty() ||
            spinnerDate.getText().toString().isEmpty() ||
            spinnerTime.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Por favor selecciona todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Crear objeto Booking con los datos seleccionados
            Booking booking = new Booking();
            booking.setClassName(spinnerClass.getText().toString());
            booking.setInstructor(spinnerInstructor.getText().toString());
            booking.setDate(spinnerDate.getText().toString());
            booking.setTime(spinnerTime.getText().toString());
            booking.setLocation("RitmoFit Centro"); // Por defecto
            booking.setDuration("60 min");
            booking.setCapacity(20);
            booking.setCurrentBookings(0);
            booking.setDescription("Reserva creada desde la app");

            // Usar el ViewModel para crear la reserva
            if (viewModel != null) {
                viewModel.createBooking(booking);
                Toast.makeText(getContext(), "Reserva creada exitosamente", Toast.LENGTH_SHORT).show();
                
                // Navegar de vuelta a la lista de reservas
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            } else {
                Toast.makeText(getContext(), "Error: ViewModel no inicializado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al crear la reserva: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
