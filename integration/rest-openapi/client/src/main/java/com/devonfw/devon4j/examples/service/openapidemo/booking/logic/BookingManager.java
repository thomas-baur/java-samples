package com.devonfw.devon4j.examples.service.openapidemo.booking.logic;

import com.devonfw.devon4j.generated.client.handler.ApiException;
import com.devonfw.devon4j.generated.client.model.BookingTO;
import com.devonfw.devon4j.generated.client.service.BookingApi;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * The BookingManger class is collection of functions that interact with the Booking api
 * In a real usecase extra business logic can be added here
 */
@Component
public class BookingManager {

    private BookingApi bookingApi;

    public BookingManager(BookingApi bookingApi) {
        this.bookingApi = bookingApi;
    }

    /**
     * Function to get all Bookings from the Api by using the generated client
     * @return returns all bookings
     */
    public Collection<BookingTO> getAllBookings() throws ApiException {
        return bookingApi.getBookingAll();
    }

    /**
     * Function to get a single Booking from the Api by using the generated client
     * @return returns one Booking
     */
    public BookingTO getBookingById(long bookingId) throws ApiException {
        return bookingApi.getBookingById(bookingId);
    }

    /**
     * Function to create a Booking from the Api by using the generated client
     * @return returns the created Booking
     */
    public BookingTO createBooking(BookingTO newBooking) throws ApiException {
        return bookingApi.createBooking(newBooking);
    }
}
