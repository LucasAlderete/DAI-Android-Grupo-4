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
    private String instructorSeleccionado;
    private String fechaSeleccionada;
    private String horarioSeleccionado;

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
            // Usar el ViewModel de la Activity para compartir el estado entre fragmentos
            viewModel = new ViewModelProvider(getActivity()).get(BookingViewModel.class);
            observeViewModel();
        }
    }

    private void observeViewModel() {
        if (viewModel != null) {
            // Observar errores del ViewModel
            viewModel.getError().observe(getViewLifecycleOwner(), error -> {
                if (error != null) {
                    Toast.makeText(getContext(),  error, Toast.LENGTH_LONG).show();
                }
            });

            // Observar cuando se crea exitosamente una reserva
            viewModel.getBookingCreated().observe(getViewLifecycleOwner(), bookingCreated -> {
                if (bookingCreated != null && bookingCreated) {
                    Toast.makeText(getContext(), "Reserva creada exitosamente", Toast.LENGTH_SHORT).show();
                    
                    // Navegar de vuelta a la lista de reservas
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                }
            });
        }
    }

    private void loadDataFromAPI() {
        // Cargar clases disponibles
        claseRepository.getClases(null, null, null, null, 0, 50, new ClaseRepository.ClaseCallback() {
            @Override
            public void onSuccess(List<ClaseDto> clases) {
                clasesDisponibles = clases;
                loadAdditionalData();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error al cargar clases: " + error, Toast.LENGTH_SHORT).show();
                setupSpinnersWithMockData(); // Fallback a datos mock
            }
        });
    }

    private void loadAdditionalData() {
        // Cargar instructores
        claseRepository.getInstructores(new ClaseRepository.InstructorCallback() {
            @Override
            public void onSuccess(List<InstructorDto> instructoresList) {
                instructores = instructoresList;
                loadSedes();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error al cargar instructores: " + error, Toast.LENGTH_SHORT).show();
                loadSedes(); // Continuar con sedes aunque falle instructores
            }
        });
    }

    private void loadSedes() {
        // Cargar sedes
        claseRepository.getSedes(new ClaseRepository.SedeCallback() {
            @Override
            public void onSuccess(List<SedeDto> sedesList) {
                sedes = sedesList;
                loadDisciplinas();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error al cargar sedes: " + error, Toast.LENGTH_SHORT).show();
                loadDisciplinas(); // Continuar con disciplinas aunque falle sedes
            }
        });
    }

    private void loadDisciplinas() {
        // Cargar disciplinas
        claseRepository.getDisciplinas(new ClaseRepository.DisciplinaCallback() {
            @Override
            public void onSuccess(List<DisciplinaDto> disciplinasList) {
                disciplinas = disciplinasList;
                setupSpinnersWithAPIData();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error al cargar disciplinas: " + error, Toast.LENGTH_SHORT).show();
                setupSpinnersWithAPIData(); // Configurar con los datos que tengamos
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
        
        // Configurar combo de instructores
        setupInstructorSpinner();
        
        // Configurar combo de fechas
        setupDateSpinner();
        
        // Configurar combo de horarios
        setupTimeSpinner();
        
        if (!nombresClases.isEmpty()) {
            spinnerClass.setText(nombresClases.get(0), false);
            tvSelectedClass.setText(nombresClases.get(0));
            claseSeleccionada = clasesDisponibles.get(0);
            updateClassDetails();
        }

        // Configurar listeners
        spinnerClass.setOnItemClickListener((parent, view, position, id) -> {
            claseSeleccionada = clasesDisponibles.get(position);
            tvSelectedClass.setText(claseSeleccionada.getNombre());
            updateClassDetails();
        });
    }

    private void setupInstructorSpinner() {
        final List<String> nombresInstructores;
        
        // Si hay una clase seleccionada, filtrar instructores por disciplina
        if (claseSeleccionada != null && claseSeleccionada.getDisciplina() != null) {
            // Obtener instructores que enseñan la misma disciplina
            nombresInstructores = getInstructoresPorDisciplina(claseSeleccionada.getDisciplina().getId());
        } else {
            // Si no hay clase seleccionada, mostrar todos los instructores
            List<String> todosInstructores = new ArrayList<>();
            for (InstructorDto instructor : instructores) {
                todosInstructores.add(instructor.getNombreCompleto());
            }
            nombresInstructores = todosInstructores;
        }
        
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, nombresInstructores);
        spinnerInstructor.setAdapter(instructorAdapter);
        
        spinnerInstructor.setOnItemClickListener((parent, view, position, id) -> {
            final String instructorSeleccionadoTemp = nombresInstructores.get(position);
            tvSelectedInstructor.setText(instructorSeleccionadoTemp);
            // Actualizar la variable de instancia usando un método helper
            updateInstructorSelection(instructorSeleccionadoTemp);
        });
    }

    private List<String> getInstructoresPorDisciplina(Long disciplinaId) {
        List<String> instructoresDisciplina = new ArrayList<>();
        
        // Buscar instructores que enseñan clases de la disciplina seleccionada
        for (ClaseDto clase : clasesDisponibles) {
            if (clase.getDisciplina() != null && 
                clase.getDisciplina().getId().equals(disciplinaId) &&
                clase.getInstructor() != null) {
                
                String nombreInstructor = clase.getInstructor().getNombreCompleto();
                if (!instructoresDisciplina.contains(nombreInstructor)) {
                    instructoresDisciplina.add(nombreInstructor);
                }
            }
        }
        
        return instructoresDisciplina;
    }

    private void updateInstructorSelection(String instructor) {
        instructorSeleccionado = instructor;
    }

    private void updateDateSelection(String fecha) {
        fechaSeleccionada = fecha;
    }

    private void updateTimeSelection(String horario) {
        horarioSeleccionado = horario;
    }

    private void setupDateSpinner() {
        // Obtener fechas únicas filtradas por disciplina si hay una clase seleccionada
        List<String> fechasUnicas = new ArrayList<>();
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
        
        Long disciplinaId = null;
        if (claseSeleccionada != null && claseSeleccionada.getDisciplina() != null) {
            disciplinaId = claseSeleccionada.getDisciplina().getId();
        }
        
        for (ClaseDto clase : clasesDisponibles) {
            if (clase.getFechaInicio() != null) {
                // Si hay disciplina seleccionada, filtrar por esa disciplina
                if (disciplinaId == null || 
                    (clase.getDisciplina() != null && clase.getDisciplina().getId().equals(disciplinaId))) {
                    
                    String fecha = dateFormat.format(clase.getFechaInicio());
                    if (!fechasUnicas.contains(fecha)) {
                        fechasUnicas.add(fecha);
                    }
                }
            }
        }
        
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, fechasUnicas);
        spinnerDate.setAdapter(dateAdapter);
        
        spinnerDate.setOnItemClickListener((parent, view, position, id) -> {
            String fechaSeleccionadaTemp = fechasUnicas.get(position);
            tvSelectedDate.setText(fechaSeleccionadaTemp);
            // Actualizar la variable de instancia usando un método helper
            updateDateSelection(fechaSeleccionadaTemp);
        });
    }

    private void setupTimeSpinner() {
        // Obtener horarios únicos filtrados por disciplina si hay una clase seleccionada
        List<String> horariosUnicos = new ArrayList<>();
        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
        
        Long disciplinaId = null;
        if (claseSeleccionada != null && claseSeleccionada.getDisciplina() != null) {
            disciplinaId = claseSeleccionada.getDisciplina().getId();
        }
        
        for (ClaseDto clase : clasesDisponibles) {
            if (clase.getFechaInicio() != null) {
                // Si hay disciplina seleccionada, filtrar por esa disciplina
                if (disciplinaId == null || 
                    (clase.getDisciplina() != null && clase.getDisciplina().getId().equals(disciplinaId))) {
                    
                    String horario = timeFormat.format(clase.getFechaInicio());
                    if (!horariosUnicos.contains(horario)) {
                        horariosUnicos.add(horario);
                    }
                }
            }
        }
        
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(getContext(), 
                android.R.layout.simple_dropdown_item_1line, horariosUnicos);
        spinnerTime.setAdapter(timeAdapter);
        
        spinnerTime.setOnItemClickListener((parent, view, position, id) -> {
            String horarioSeleccionadoTemp = horariosUnicos.get(position);
            tvSelectedTime.setText(horarioSeleccionadoTemp);
            // Actualizar la variable de instancia usando un método helper
            updateTimeSelection(horarioSeleccionadoTemp);
        });
    }

    private void updateClassDetails() {
        if (claseSeleccionada != null) {
            // Actualizar todos los combos con datos filtrados por disciplina
            setupInstructorSpinner();
            setupDateSpinner();
            setupTimeSpinner();
            
            // Actualizar detalles de la clase seleccionada en los TextView
            if (claseSeleccionada.getInstructor() != null) {
                instructorSeleccionado = claseSeleccionada.getInstructor().getNombreCompleto();
                tvSelectedInstructor.setText(instructorSeleccionado);
                // También actualizar el combo de instructor
                spinnerInstructor.setText(instructorSeleccionado, false);
            }
            
            if (claseSeleccionada.getSede() != null) {
                // Aquí podrías mostrar la sede si tienes un campo para ello
            }
            
            // Formatear fecha y hora
            if (claseSeleccionada.getFechaInicio() != null) {
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
                java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
                
                fechaSeleccionada = dateFormat.format(claseSeleccionada.getFechaInicio());
                horarioSeleccionado = timeFormat.format(claseSeleccionada.getFechaInicio());
                
                tvSelectedDate.setText(fechaSeleccionada);
                tvSelectedTime.setText(horarioSeleccionado);
                
                // También actualizar los combos
                spinnerDate.setText(fechaSeleccionada, false);
                spinnerTime.setText(horarioSeleccionado, false);
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
                // El mensaje de éxito se mostrará cuando se complete la operación exitosamente
                // a través del observer en observeViewModel()
            } else {
                Toast.makeText(getContext(), "Error: ViewModel no inicializado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
