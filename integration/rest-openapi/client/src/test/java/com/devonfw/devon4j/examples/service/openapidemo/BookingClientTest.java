package com.devonfw.devon4j.examples.service.openapidemo;

import com.devonfw.devon4j.generated.client.handler.ApiClient;
import com.devonfw.devon4j.generated.client.handler.ApiException;
import com.devonfw.devon4j.generated.client.model.BookingTO;
import com.devonfw.devon4j.examples.service.openapidemo.booking.logic.BookingManager;
import com.devonfw.devon4j.generated.client.service.BookingApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingClientTest {

    private final String basePath = "/api/v1";
    private ApiClient apiClient = new ApiClient().setBasePath(basePath);
    private BookingApi bookingApi = new BookingApi(apiClient);
    private BookingManager bookingManager = new BookingManager(bookingApi);

    private WireMockServer wireMockServer;

    //Make sure the mock server is started before each test
    @BeforeEach
    void startWireMock() {
        wireMockServer = new WireMockServer(
            WireMockConfiguration.wireMockConfig().port(8080)
        );
        wireMockServer.start();
    }

    @AfterEach
    void stopWireMock() {
        wireMockServer.stop();
    }
    
    @Test
    public void testGetOneBookingClient() throws JsonProcessingException, ApiException {
        wireMockServer.stubFor(get("/api/v1/booking/1")
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withHeader(HttpHeaders.CONNECTION, "close")
                .withBody("{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":[]}")));

        BookingTO result = bookingManager.getBookingById(1L);
        String resultString = new ObjectMapper().writeValueAsString(result);
        assertEquals("{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":[]}" ,resultString);
    }

    @Test
    public void testGetAllBookingsClient() throws JsonProcessingException, ApiException {
        wireMockServer.stubFor(get("/api/v1/booking")
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withHeader(HttpHeaders.CONNECTION, "close")
                        .withBody("[{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":[]}]")));

        Collection<BookingTO> resultList = bookingManager.getAllBookings();
        String resultString = new ObjectMapper().writeValueAsString(resultList);
        assertEquals("[{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":[]}]" ,resultString);
    }

    @Test
    public void testCreateBookingClient() throws JsonProcessingException, ApiException {
        wireMockServer.
            stubFor(post("/api/v1/booking")
            .willReturn(created()
                    .withHeader("Content-Type", "application/json")
                    .withHeader(HttpHeaders.CONNECTION, "close")
                    .withBody("{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":[]}")));

        BookingTO newBooking = new BookingTO().id(1L).modificationCounter(0);

        BookingTO result = bookingManager.createBooking(newBooking);
        String resultString = new ObjectMapper().writeValueAsString(result);
        assertEquals("{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":[]}" ,resultString);
    }
}
