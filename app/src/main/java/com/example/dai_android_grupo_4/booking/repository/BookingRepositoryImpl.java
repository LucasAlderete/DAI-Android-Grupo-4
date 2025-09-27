package com.example.dai_android_grupo_4.booking.repository;

import com.example.dai_android_grupo_4.booking.api.BookingService;
import com.example.dai_android_grupo_4.booking.model.Booking;
import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import com.example.dai_android_grupo_4.booking.model.CreateBookingRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class BookingRepositoryImpl implements BookingRepository {

    private final ApiService apiService;
    private final Gson gson;

    private final BookingService bookingService;

    @Inject
    public BookingRepositoryImpl(BookingService bookingService,ApiService apiService, Gson gson) {
        this.bookingService = bookingService;
        this.apiService = apiService;
        this.gson = gson;
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
                        String errorMessage = extractErrorMessage(response);
                        callback.onError(errorMessage);
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
                    String errorMessage = extractErrorMessage(response);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ReservaDto> call, Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

        @Override
        public void createBooking(long claseId, String token, SingleBookingCallback callback) {
            CreateBookingRequest request = new CreateBookingRequest(claseId);
            bookingService.createBooking("Bearer " + token, request).enqueue(new Callback<Booking>() {
                @Override
                public void onResponse(Call<Booking> call, Response<Booking> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError("Error al crear la reserva");
                    }
                }

                @Override
                public void onFailure(Call<Booking> call, Throwable t) {
                    callback.onError(t.getMessage());
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
                    String instructorName = clase.getInstructor().getNombreCompleto();
                    booking.setInstructor(instructorName);
                    android.util.Log.d("BookingRepo", "Instructor encontrado: " + instructorName);
                } else {
                    booking.setInstructor("Instructor no asignado");
                    android.util.Log.d("BookingRepo", "Instructor es null para reserva: " + reserva.getId());
                }

                if (clase.getSede() != null) {
                    String sedeName = clase.getSede().getNombre();
                    booking.setLocation(sedeName);
                    android.util.Log.d("BookingRepo", "Sede encontrada: " + sedeName);
                } else {
                    booking.setLocation("Sede no asignada");
                    android.util.Log.d("BookingRepo", "Sede es null para reserva: " + reserva.getId());
                }
            } else {
                android.util.Log.d("BookingRepo", "Clase es null para reserva: " + reserva.getId());
                booking.setInstructor("Datos no disponibles");
                booking.setLocation("Datos no disponibles");
            }

            bookings.add(booking);
        }

        return bookings;
    }

    /**
     * Extrae el mensaje de error específico del cuerpo de la respuesta HTTP
     */
    private String extractErrorMessage(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                String errorBody = response.errorBody().string();
                ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);
                return errorResponse.getSpecificErrorMessage();
            }
        } catch (IOException | JsonSyntaxException e) {
            // Si no se puede parsear el error, usar el código de estado
        }

        // Fallback al código de error si no se puede extraer el mensaje
        return "Error al crear reserva: " + response.code();
    }
}
