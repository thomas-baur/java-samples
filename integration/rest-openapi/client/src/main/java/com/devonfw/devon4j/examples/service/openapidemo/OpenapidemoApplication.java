package com.devonfw.devon4j.examples.service.openapidemo;

import com.devonfw.devon4j.generated.client.handler.ApiException;
import com.devonfw.devon4j.generated.client.model.BookingTO;
import com.devonfw.devon4j.examples.service.openapidemo.booking.logic.BookingManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;

/**
 * Small example main application to get a list of all bookings
 */
@SpringBootApplication
public class OpenapidemoApplication implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(OpenapidemoApplication.class);

	private BookingManager bookingManager;

	public OpenapidemoApplication(BookingManager bookingManager) {
		this.bookingManager = bookingManager;
	}

	public static void main(String[] args) {
		SpringApplication.run(OpenapidemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try {
			Collection<BookingTO> bookings = bookingManager.getAllBookings();

			String bookingString = new ObjectMapper().writeValueAsString(bookings);

			log.info(bookingString);
		}catch (ApiException e){
			log.error("Make sure the test server is running");
		}
	}
}
