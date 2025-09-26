package com.example.dai_android_grupo_4.booking.ui.fragments;

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

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.model.Booking;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingDetailFragment extends Fragment {

    private static final String ARG_BOOKING = "booking";
    
    private Booking booking;
    private TextView tvClassName, tvInstructor, tvDate, tvTime, tvLocation, tvStatus;
    private Button btnCancel, btnGetDirections;

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
        setupClickListeners();
        populateData();
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

    private void setupClickListeners() {
        btnCancel.setOnClickListener(v -> {
            // Implementar cancelación de reserva
            Toast.makeText(getContext(), "Funcionalidad de cancelación en desarrollo", Toast.LENGTH_SHORT).show();
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
}
