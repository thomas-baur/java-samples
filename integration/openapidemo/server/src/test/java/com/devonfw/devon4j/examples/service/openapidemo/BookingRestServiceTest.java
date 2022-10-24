package com.devonfw.devon4j.examples.service.openapidemo;

import com.devonfw.devon4j.generated.api.model.BookingTO;
import com.devonfw.devon4j.examples.service.openapidemo.booking.logic.BookingManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class BookingRestServiceTest {

    @MockBean
    private BookingManager bookingManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<BookingTO> bookingCaptor;

    @Test
    public void testGetAllBookingsEndpoint() throws Exception {

        List<BookingTO> mockResult = new ArrayList<>();

        for (long i = 0; i < 10; i++) {
            BookingTO mockResultEntity = new BookingTO();
            mockResultEntity.id(i).setModificationCounter(0);
            mockResult.add(mockResultEntity);
        }

        Mockito.when(this.bookingManager.getAllBookings()).thenReturn(mockResult);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/booking")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertEquals(
                "[{\"id\":0,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":2,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":3,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":4,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":5,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":6,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":7,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":8,\"modificationCounter\":0,\"invitedGuests\":null},{\"id\":9,\"modificationCounter\":0,\"invitedGuests\":null}]",
                contentAsString
        );
    }

    @Test
    public void testCreateBookingEndpoint() throws Exception {

        BookingTO mockResult = new BookingTO();
        mockResult.id(1L).setModificationCounter(0);

        Mockito.when(this.bookingManager.createBooking(mockResult)).thenReturn(mockResult);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/booking")
            .content(objectMapper.writeValueAsString(mockResult))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertEquals(objectMapper.writeValueAsString(mockResult), contentAsString);

        Mockito.verify(this.bookingManager).createBooking(this.bookingCaptor.capture());

        BookingTO verifyBooking = this.bookingCaptor.getValue();

        assertEquals(
                "{\"id\":1,\"modificationCounter\":0,\"invitedGuests\":null}",
                objectMapper.writeValueAsString(verifyBooking)
        );
    }
}
