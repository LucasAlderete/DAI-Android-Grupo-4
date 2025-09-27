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
import com.example.dai_android_grupo_4.booking.repository.ClaseRepository;
import com.example.dai_android_grupo_4.booking.ui.viewmodel.BookingViewModel;
import com.example.dai_android_grupo_4.data.api.model.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateBookingFragment extends Fragment {

    @Inject
    ClaseRepository claseRepository;

    private AutoCompleteTextView spinnerClass, spinnerInstructor, spinnerDate, spinnerTime;
    private TextView tvSelectedClass, tvSelectedInstructor, tvSelectedDate, tvSelectedTime;
    private MaterialButton btnCreateBooking;
    private BookingViewModel viewModel;
    
    // Listas de datos de la API
    private List<ClaseDto> clasesDisponibles = new ArrayList<>();
    private List<SedeDto> sedes = new ArrayList<>();
    private List<InstructorDto> instructores = new ArrayList<>();
    private List<DisciplinaDto> disciplinas = new ArrayList<>();
    
    // Selecciones actuales
    private ClaseDto claseSeleccionada;

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
        loadDataFromAPI();
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

    private void loadDataFromAPI() {
        // Cargar clases disponibles
        claseRepository.getClases(null, null, null, null, 0, 50, new ClaseRepository.ClaseCallback() {
            @Override
            public void onSuccess(List<ClaseDto> clases) {
                clasesDisponibles = clases;
                setupSpinnersWithAPIData();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error al cargar clases: " + error, Toast.LENGTH_SHORT).show();
                setupSpinnersWithMockData(); // Fallback a datos mock
            }
        });
    }

    private void setupSpinnersWithMockData() {
        // Si falla la carga de datos de la API, mostrar mensaje de error
        Toast.makeText(getContext(), "No se pudieron cargar las clases. Verifica tu conexión a internet.", Toast.LENGTH_LONG).show();
    }

    private void setupSpinnersWithAPIData() {
        // Configurar AutoCompleteTextView de clases con datos reales
        List<String> nombresClases = new ArrayList<>();
        for (ClaseDto clase : clasesDisponibles) {
            nombresClases.add(clase.getNombre());
        }
        
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, nombresClases);
        spinnerClass.setAdapter(classAdapter);
        
        if (!nombresClases.isEmpty()) {
            spinnerClass.setText(nombresClases.get(0), false);
            tvSelectedClass.setText(nombresClases.get(0));
            claseSeleccionada = clasesDisponibles.get(0);
        }

        // Configurar listeners
        spinnerClass.setOnItemClickListener((parent, view, position, id) -> {
            claseSeleccionada = clasesDisponibles.get(position);
            tvSelectedClass.setText(claseSeleccionada.getNombre());
            updateClassDetails();
        });
    }

    private void updateClassDetails() {
        if (claseSeleccionada != null) {
            // Actualizar detalles de la clase seleccionada
            if (claseSeleccionada.getInstructor() != null) {
                tvSelectedInstructor.setText(claseSeleccionada.getInstructor().getNombreCompleto());
            }
            
            if (claseSeleccionada.getSede() != null) {
                // Aquí podrías mostrar la sede si tienes un campo para ello
            }
            
            // Formatear fecha y hora
            if (claseSeleccionada.getFechaInicio() != null) {
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
                java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
                
                tvSelectedDate.setText(dateFormat.format(claseSeleccionada.getFechaInicio()));
                tvSelectedTime.setText(timeFormat.format(claseSeleccionada.getFechaInicio()));
            }
        }
    }

    private void setupClickListeners() {
        btnCreateBooking.setOnClickListener(v -> {
            createBooking();
        });
    }

    private void createBooking() {
        // Validar que se haya seleccionado una clase
        if (claseSeleccionada == null) {
            Toast.makeText(getContext(), "Por favor selecciona una clase", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que la clase tenga cupo disponible
        if (!claseSeleccionada.hasAvailableSpots()) {
            Toast.makeText(getContext(), "Esta clase no tiene cupo disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Crear objeto Booking con los datos de la clase seleccionada
            Booking booking = new Booking();
            booking.setClaseId(claseSeleccionada.getId());
            booking.setClassName(claseSeleccionada.getNombre());
            booking.setDescription(claseSeleccionada.getDescripcion());
            
            if (claseSeleccionada.getInstructor() != null) {
                booking.setInstructor(claseSeleccionada.getInstructor().getNombreCompleto());
            }
            
            if (claseSeleccionada.getSede() != null) {
                booking.setLocation(claseSeleccionada.getSede().getNombre());
            }
            
            if (claseSeleccionada.getFechaInicio() != null) {
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
                java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
                booking.setDate(dateFormat.format(claseSeleccionada.getFechaInicio()));
                booking.setTime(timeFormat.format(claseSeleccionada.getFechaInicio()));
            }
            
            booking.setCapacity(claseSeleccionada.getCupoMaximo() != null ? claseSeleccionada.getCupoMaximo() : 0);
            booking.setCurrentBookings(claseSeleccionada.getCupoActual() != null ? claseSeleccionada.getCupoActual() : 0);

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
