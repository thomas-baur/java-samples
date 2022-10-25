package com.devonfw.devon4j.examples.service.openapidemo.booking.logic;

import com.devonfw.devon4j.generated.api.model.BookingTO;
import com.devonfw.devon4j.generated.api.model.InvitedGuestTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The BookingManager is responsible for all logic
 * In this example it's only purpose is serving static data
 * In a real usecase business logic can be added here
 */
@Component
public class BookingManager {

    /**
     * Function to generate example date
     * @return 10 Bookings with 0 to 9 guests
     */
    private List<BookingTO> getBookingList(){

        List<BookingTO> bookingList = new ArrayList<>();
        List<InvitedGuestTO> guestList = new ArrayList<>();

        for (long i = 0; i < 10; i++) {
            InvitedGuestTO newGuest = new InvitedGuestTO()
                .id(i)
                .modificationCounter(0)
                .email("guest"+i+".email@email.com");
            guestList.add(newGuest);
        }

        for (long i = 0; i < 10; i++) {
            BookingTO newBooking = new BookingTO()
                .id(i)
                .modificationCounter(0);
            for(int j = 0; j <= i; j++){
                newBooking.addInvitedGuestsItem(guestList.get(j));
            }
            bookingList.add(newBooking);
        }
        return bookingList;
    }

    /**
     * Find a booking by id
     * @param id of booking
     * @return the found Booking
     */
    public BookingTO getBooking(Long id) {
        return getBookingList().get(5).id(id);
    }

    /**
     * Return all bookings
     * @return all 10 bookings
     */
    public Collection<BookingTO> getAllBookings() {
        return getBookingList();
    }

    /**
     * Should create a new booking (not implemented)
     * @return returns the booking from request
     */
    public BookingTO createBooking(BookingTO bookingTO) {
        return bookingTO;
    }

}
