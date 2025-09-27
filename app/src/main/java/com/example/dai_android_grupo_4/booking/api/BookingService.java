package com.example.dai_android_grupo_4.booking.api;

import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.booking.model.CreateBookingRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BookingService {

    @POST("reservas")
    Call<Booking> createBooking(@Header("Authorization") String token, @Body CreateBookingRequest request);
}
