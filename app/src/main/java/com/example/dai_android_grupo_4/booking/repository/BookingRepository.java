package com.example.dai_android_grupo_4.booking.repository;

import com.example.dai_android_grupo_4.booking.model.Booking;

import java.util.List;

public interface BookingRepository {

    interface BookingCallback {
        void onSuccess(List<Booking> bookings);
        void onError(String error);
    }

    interface SingleBookingCallback {
        void onSuccess(Booking booking);
        void onError(String error);
    }

    void getUserBookings(BookingCallback callback);
    void getFilteredBookings(String status, String date, BookingCallback callback);
    void cancelBooking(String bookingId, BookingCallback callback);
    void createBooking(Booking booking, BookingCallback callback);
    void createBooking(long claseId, String token, SingleBookingCallback callback);
    void getBookingById(String bookingId, BookingCallback callback);
}
