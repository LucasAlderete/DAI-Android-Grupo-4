package com.example.dai_android_grupo_4.booking.ui.fragments;

import android.app.AlertDialog;
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
import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.booking.ui.viewmodel.BookingViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingDetailFragment extends Fragment {

    private static final String ARG_BOOKING = "booking";
    
    private Booking booking;
    private TextView tvClassName, tvInstructor, tvDate, tvTime, tvLocation, tvStatus;
    private Button btnCancel, btnGetDirections;
    private BookingViewModel viewModel;

    public static BookingDetailFragment newInstance(Booking booking) {
        BookingDetailFragment fragment = new BookingDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOKING, booking);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            booking = (Booking) getArguments().getSerializable(ARG_BOOKING);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupViewModel();
        setupClickListeners();
        populateData();
        observeViewModel();
    }

    private void initViews(View view) {
        tvClassName = view.findViewById(R.id.tv_class_name);
        tvInstructor = view.findViewById(R.id.tv_instructor);
        tvDate = view.findViewById(R.id.tv_date);
        tvTime = view.findViewById(R.id.tv_time);
        tvLocation = view.findViewById(R.id.tv_location);
        tvStatus = view.findViewById(R.id.tv_status);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnGetDirections = view.findViewById(R.id.btn_get_directions);
    }

    private void setupViewModel() {
        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity()).get(BookingViewModel.class);
        }
    }

    private void observeViewModel() {
        if (viewModel != null) {
            // Observar errores del ViewModel
            viewModel.getError().observe(getViewLifecycleOwner(), error -> {
                if (error != null) {
                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                }
            });

            // Observar cuando se cancela exitosamente una reserva
            viewModel.getBookingCanceled().observe(getViewLifecycleOwner(), bookingCanceled -> {
                if (bookingCanceled != null && bookingCanceled) {
                    Toast.makeText(getContext(), "Reserva cancelada exitosamente", Toast.LENGTH_SHORT).show();
                    
                    // Navegar de vuelta a la lista de reservas
                    if (getActivity() != null) {
                        getActivity().onBackPressed();
                    }
                }
            });
        }
    }

    private void setupClickListeners() {
        btnCancel.setOnClickListener(v -> {
            showCancelConfirmationDialog();
        });

        btnGetDirections.setOnClickListener(v -> {
            // Abrir Google Maps con la dirección
            Toast.makeText(getContext(), "Abriendo Google Maps...", Toast.LENGTH_SHORT).show();
        });
    }

    private void populateData() {
        if (booking != null) {
            tvClassName.setText(booking.getClassName());
            tvInstructor.setText(booking.getInstructor());
            tvDate.setText(booking.getDate());
            tvTime.setText(booking.getTime());
            tvLocation.setText(booking.getLocation());
            tvStatus.setText(booking.getStatus());
            
            // Configurar visibilidad del botón de cancelar según el estado
            if ("CANCELED".equals(booking.getStatus()) || "COMPLETED".equals(booking.getStatus())) {
                btnCancel.setVisibility(View.GONE);
            }
        }
    }

    private void showCancelConfirmationDialog() {
        if (getContext() == null) return;

        new AlertDialog.Builder(getContext())
                .setTitle("Cancelar Reserva")
                .setMessage("¿Estás seguro de que quieres cancelar esta reserva? Esta acción no se puede deshacer.")
                .setPositiveButton("Cancelar Reserva", (dialog, which) -> {
                    if (viewModel != null && booking != null) {
                        viewModel.cancelBooking(booking.getId());
                    }
                })
                .setNegativeButton("Mantener Reserva", null)
                .show();
    }
}
