package com.example.dai_android_grupo_4.booking.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.booking.repository.BookingRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookingViewModel extends ViewModel {

    private final BookingRepository bookingRepository;
    
    private final MutableLiveData<List<Booking>> _bookings = new MutableLiveData<>();
    public LiveData<List<Booking>> getBookings() { return _bookings; }

    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>();
    public LiveData<Boolean> getLoading() { return _loading; }

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> getError() { return _error; }

    private final MutableLiveData<Boolean> _bookingCreated = new MutableLiveData<>();
    public LiveData<Boolean> getBookingCreated() { return _bookingCreated; }

    private final MutableLiveData<Boolean> _bookingCanceled = new MutableLiveData<>();
    public LiveData<Boolean> getBookingCanceled() { return _bookingCanceled; }

    @Inject
    public BookingViewModel(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        loadUserBookings();
    }

    public void loadUserBookings() {
        _loading.setValue(true);
        _error.setValue(null);
        
        bookingRepository.getUserBookings(new BookingRepository.BookingCallback() {
            @Override
            public void onSuccess(List<Booking> bookings) {
                _bookings.setValue(bookings);
                _loading.setValue(false);
            }

            @Override
            public void onError(String error) {
                _error.setValue(error);
                _loading.setValue(false);
            }
        });
    }

    public void cancelBooking(String bookingId) {
        _loading.setValue(true);
        _error.setValue(null);
        _bookingCanceled.setValue(false);
        
        bookingRepository.cancelBooking(bookingId, new BookingRepository.BookingCallback() {
            @Override
            public void onSuccess(List<Booking> bookings) {
                _bookingCanceled.setValue(true);
                // Recargar la lista después de cancelar
                loadUserBookings();
            }

            @Override
            public void onError(String error) {
                _error.setValue(error);
                _loading.setValue(false);
                _bookingCanceled.setValue(false);
            }
        });
    }

    public void filterBookings(String status, String date) {
        _loading.setValue(true);
        
        bookingRepository.getFilteredBookings(status, date, new BookingRepository.BookingCallback() {
            @Override
            public void onSuccess(List<Booking> bookings) {
                _bookings.setValue(bookings);
                _loading.setValue(false);
            }

            @Override
            public void onError(String error) {
                _error.setValue(error);
                _loading.setValue(false);
            }
        });
    }

    public void refreshBookings() {
        loadUserBookings();
    }

    public void createBooking(Booking booking) {
        _loading.setValue(true);
        _error.setValue(null);
        _bookingCreated.setValue(false);
        
        bookingRepository.createBooking(booking, new BookingRepository.BookingCallback() {
            @Override
            public void onSuccess(List<Booking> bookings) {
                _bookingCreated.setValue(true);
                // Recargar la lista después de crear la reserva
                loadUserBookings();
            }

            @Override
            public void onError(String error) {
                _error.setValue(error);
                _loading.setValue(false);
                _bookingCreated.setValue(false);
            }
        });
    }
}
