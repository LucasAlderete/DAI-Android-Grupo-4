package com.example.dai_android_grupo_4.booking.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.ui.adapter.BookingAdapter;
import com.example.dai_android_grupo_4.booking.ui.viewmodel.BookingViewModel;
import com.example.dai_android_grupo_4.booking.model.Booking;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingListFragment extends Fragment implements BookingAdapter.OnBookingClickListener {

    private RecyclerView recyclerView;
    private CircularProgressIndicator progressBar;
    private FloatingActionButton fabCreateBooking;
    private BookingAdapter adapter;
    private BookingViewModel viewModel;
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupRecyclerView();
        setupFloatingActionButton();
        setupViewModel();
        observeData();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_bookings);
        progressBar = view.findViewById(R.id.progress_bar);
        fabCreateBooking = view.findViewById(R.id.fab_create_booking);
    }

    private void setupRecyclerView() {
        adapter = new BookingAdapter(bookings, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupFloatingActionButton() {
        fabCreateBooking.setOnClickListener(v -> {
            CreateBookingFragment createBookingFragment = new CreateBookingFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, createBookingFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
    }

    private void observeData() {
        viewModel.getBookings().observe(getViewLifecycleOwner(), bookings -> {
            this.bookings.clear();
            this.bookings.addAll(bookings);
            adapter.notifyDataSetChanged();
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBookingClick(Booking booking) {
        // Navegar al detalle de la reserva
        BookingDetailFragment detailFragment = BookingDetailFragment.newInstance(booking);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelBooking(Booking booking) {
        viewModel.cancelBooking(booking.getId());
    }
}
