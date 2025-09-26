package com.example.dai_android_grupo_4.booking.repository;

import com.example.dai_android_grupo_4.booking.model.Booking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class BookingRepositoryImpl implements BookingRepository {

    @Inject
    public BookingRepositoryImpl() {
        // Constructor sin dependencias para evitar ciclos
    }

    @Override
    public void getUserBookings(BookingCallback callback) {
        // Por ahora retornamos datos mock, después se integrará con el servicio real
        List<Booking> mockBookings = createMockBookings();
        callback.onSuccess(mockBookings);
    }

    @Override
    public void getFilteredBookings(String status, String date, BookingCallback callback) {
        List<Booking> allBookings = createMockBookings();
        List<Booking> filteredBookings = new ArrayList<>();

        for (Booking booking : allBookings) {
            boolean matchesStatus = status == null || status.isEmpty() || status.equals(booking.getStatus());
            boolean matchesDate = date == null || date.isEmpty() || date.equals(booking.getDate());
            
            if (matchesStatus && matchesDate) {
                filteredBookings.add(booking);
            }
        }

        callback.onSuccess(filteredBookings);
    }

    @Override
    public void cancelBooking(String bookingId, BookingCallback callback) {
        // Simular cancelación
        List<Booking> updatedBookings = createMockBookings();
        for (Booking booking : updatedBookings) {
            if (booking.getId().equals(bookingId)) {
                booking.setStatus("CANCELED");
                break;
            }
        }
        callback.onSuccess(updatedBookings);
    }

    @Override
    public void createBooking(Booking booking, BookingCallback callback) {
        // Simular creación de reserva
        List<Booking> updatedBookings = createMockBookings();
        booking.setId("booking_" + System.currentTimeMillis());
        booking.setStatus("CONFIRMED");
        booking.setCreatedAt(new Date());
        updatedBookings.add(booking);
        callback.onSuccess(updatedBookings);
    }

    @Override
    public void getBookingById(String bookingId, BookingCallback callback) {
        List<Booking> allBookings = createMockBookings();
        for (Booking booking : allBookings) {
            if (booking.getId().equals(bookingId)) {
                List<Booking> singleBooking = new ArrayList<>();
                singleBooking.add(booking);
                callback.onSuccess(singleBooking);
                return;
            }
        }
        callback.onError("Reserva no encontrada");
    }

    private List<Booking> createMockBookings() {
        List<Booking> bookings = new ArrayList<>();

        // Reserva 1 - Confirmada
        Booking booking1 = new Booking();
        booking1.setId("booking_1");
        booking1.setClassName("Yoga Matutino");
        booking1.setInstructor("María González");
        booking1.setDate("15 de Marzo, 2024");
        booking1.setTime("09:00 - 10:00");
        booking1.setLocation("RitmoFit Centro");
        booking1.setStatus("CONFIRMED");
        booking1.setDuration("60 min");
        booking1.setCapacity(20);
        booking1.setCurrentBookings(15);
        booking1.setDescription("Clase de yoga para principiantes");
        bookings.add(booking1);

        // Reserva 2 - Confirmada
        Booking booking2 = new Booking();
        booking2.setId("booking_2");
        booking2.setClassName("Pilates Avanzado");
        booking2.setInstructor("Carlos Ruiz");
        booking2.setDate("16 de Marzo, 2024");
        booking2.setTime("18:00 - 19:00");
        booking2.setLocation("RitmoFit Norte");
        booking2.setStatus("CONFIRMED");
        booking2.setDuration("60 min");
        booking2.setCapacity(15);
        booking2.setCurrentBookings(12);
        booking2.setDescription("Clase de pilates para nivel avanzado");
        bookings.add(booking2);

        // Reserva 3 - Completada
        Booking booking3 = new Booking();
        booking3.setId("booking_3");
        booking3.setClassName("Spinning");
        booking3.setInstructor("Ana Martínez");
        booking3.setDate("10 de Marzo, 2024");
        booking3.setTime("19:00 - 20:00");
        booking3.setLocation("RitmoFit Centro");
        booking3.setStatus("COMPLETED");
        booking3.setDuration("60 min");
        booking3.setCapacity(25);
        booking3.setCurrentBookings(25);
        booking3.setDescription("Clase de spinning intensiva");
        bookings.add(booking3);

        // Reserva 4 - Cancelada
        Booking booking4 = new Booking();
        booking4.setId("booking_4");
        booking4.setClassName("CrossFit");
        booking4.setInstructor("Roberto Silva");
        booking4.setDate("12 de Marzo, 2024");
        booking4.setTime("07:00 - 08:00");
        booking4.setLocation("RitmoFit Sur");
        booking4.setStatus("CANCELED");
        booking4.setDuration("60 min");
        booking4.setCapacity(12);
        booking4.setCurrentBookings(8);
        booking4.setDescription("Entrenamiento funcional intenso");
        bookings.add(booking4);

        return bookings;
    }
}
