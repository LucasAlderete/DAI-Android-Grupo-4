package com.example.dai_android_grupo_4.booking.repository;

import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepositoryImpl implements BookingRepository {

    private final ApiService apiService;

    @Inject
    public BookingRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getUserBookings(BookingCallback callback) {
        apiService.getMisReservas(0, 20).enqueue(new Callback<PageReservaDto>() {
            @Override
            public void onResponse(Call<PageReservaDto> call, Response<PageReservaDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Booking> bookings = convertToBookingList(response.body().getContent());
                    callback.onSuccess(bookings);
                } else {
                    callback.onError("Error al obtener reservas: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PageReservaDto> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void getFilteredBookings(String status, String date, BookingCallback callback) {
        // Por ahora obtenemos todas las reservas y filtramos localmente
        // En el futuro se podría implementar filtrado en el servidor
        getUserBookings(new BookingCallback() {
            @Override
            public void onSuccess(List<Booking> bookings) {
                List<Booking> filteredBookings = new ArrayList<>();
                
                for (Booking booking : bookings) {
                    boolean matchesStatus = status == null || status.isEmpty() || status.equals(booking.getStatus());
                    boolean matchesDate = date == null || date.isEmpty() || date.equals(booking.getDate());
                    
                    if (matchesStatus && matchesDate) {
                        filteredBookings.add(booking);
                    }
                }
                
                callback.onSuccess(filteredBookings);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void cancelBooking(String bookingId, BookingCallback callback) {
        try {
            Long id = Long.parseLong(bookingId);
            apiService.cancelarReserva(id).enqueue(new Callback<ReservaDto>() {
                @Override
                public void onResponse(Call<ReservaDto> call, Response<ReservaDto> response) {
                    if (response.isSuccessful()) {
                        // Recargar la lista de reservas después de cancelar
                        getUserBookings(callback);
                    } else {
                        callback.onError("Error al cancelar reserva: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ReservaDto> call, Throwable t) {
                    callback.onError("Error de conexión: " + t.getMessage());
                }
            });
        } catch (NumberFormatException e) {
            callback.onError("ID de reserva inválido");
        }
    }

    @Override
    public void createBooking(Booking booking, BookingCallback callback) {
        // Extraer el claseId del booking
        Long claseId = booking.getClaseId();
        
        if (claseId == null) {
            callback.onError("ID de clase no válido");
            return;
        }
        
        CrearReservaDto crearReservaDto = new CrearReservaDto(claseId);
        
        apiService.crearReserva(crearReservaDto).enqueue(new Callback<ReservaDto>() {
            @Override
            public void onResponse(Call<ReservaDto> call, Response<ReservaDto> response) {
                if (response.isSuccessful()) {
                    // Recargar la lista de reservas después de crear
                    getUserBookings(callback);
                } else {
                    callback.onError("Error al crear reserva: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ReservaDto> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void getBookingById(String bookingId, BookingCallback callback) {
        // Por ahora obtenemos todas las reservas y buscamos por ID
        // En el futuro se podría implementar un endpoint específico
        getUserBookings(new BookingCallback() {
            @Override
            public void onSuccess(List<Booking> bookings) {
                for (Booking booking : bookings) {
                    if (booking.getId().equals(bookingId)) {
                        List<Booking> singleBooking = new ArrayList<>();
                        singleBooking.add(booking);
                        callback.onSuccess(singleBooking);
                        return;
                    }
                }
                callback.onError("Reserva no encontrada");
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    // Métodos auxiliares
    private List<Booking> convertToBookingList(List<ReservaDto> reservas) {
        List<Booking> bookings = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        
        for (ReservaDto reserva : reservas) {
            Booking booking = new Booking();
            booking.setId(reserva.getId().toString());
            booking.setClaseId(reserva.getClaseId());
            booking.setStatus(reserva.getEstado());
            booking.setCreatedAt(reserva.getFechaReserva());
            
            if (reserva.getClase() != null) {
                ClaseDto clase = reserva.getClase();
                booking.setClassName(clase.getNombre());
                booking.setDescription(clase.getDescripcion());
                booking.setCapacity(clase.getCupoMaximo() != null ? clase.getCupoMaximo() : 0);
                booking.setCurrentBookings(clase.getCupoActual() != null ? clase.getCupoActual() : 0);
                
                if (clase.getFechaInicio() != null) {
                    booking.setDate(dateFormat.format(clase.getFechaInicio()));
                }
                
                if (clase.getInstructor() != null) {
                    booking.setInstructor(clase.getInstructor().getNombreCompleto());
                }
                
                if (clase.getSede() != null) {
                    booking.setLocation(clase.getSede().getNombre());
                }
            }
            
            bookings.add(booking);
        }
        
        return bookings;
    }
}
