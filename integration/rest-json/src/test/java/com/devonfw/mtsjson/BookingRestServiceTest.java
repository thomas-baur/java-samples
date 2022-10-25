package com.devonfw.mtsjson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.devonfw.mtsjson.bookingmanagement.logic.UcManageBooking;
import com.devonfw.mtsjson.bookingmanagement.service.model.BookingTo;
import com.devonfw.mtsjson.bookingmanagement.service.model.InvitedAdultTo;
import com.devonfw.mtsjson.bookingmanagement.service.model.InvitedChildTo;
import com.devonfw.mtsjson.bookingmanagement.service.model.InvitedGuestTo;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class BookingRestServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(MtsJsonApplication.class);

  @MockBean
  private UcManageBooking ucManageBooking;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Captor
  ArgumentCaptor<List<BookingTo>> bookingCaptor;

  private InvitedAdultTo invitedAdult;

  private InvitedChildTo invitedChild;

  private static final ZonedDateTime BOOKED_AT_INDIA = ZonedDateTime.parse("2022-09-09T12:51:40+05:30");

  private static final ZonedDateTime BOOKING_DATE_INDIA = ZonedDateTime.parse("2022-09-10T17:30:00+05:30");

  private static final ZonedDateTime BOOKING_DATE_GERMANY = ZonedDateTime.parse("2022-09-10T13:30:00+01:00");

  private List<InvitedGuestTo> invitedGuests = new ArrayList<>();

  private MonetaryAmount reservationFee;

  @Test
  void contextLoads() {

  }

  @BeforeEach
  public void loadData() {

    this.invitedAdult = new InvitedAdultTo();
    this.invitedAdult.setId(1L);
    this.invitedAdult.setModificationCounter(0);
    this.invitedAdult.setEmail("user@gmail.com");

    this.invitedChild = new InvitedChildTo();
    this.invitedChild.setId(2L);
    this.invitedChild.setModificationCounter(0);
    this.invitedChild.setNeedsSpecialChair(true);

    this.invitedGuests.add(this.invitedAdult);
    this.invitedGuests.add(this.invitedChild);
    Money moneyof = Money.of(200, "USD");
    this.reservationFee = moneyof;

  }

  /**
   * @Input : When given BookedAt and BookingDate are of Indian Timezone
   * @Output: Returned Date should be in UTC timezone
   */
  @Test
  public void testGetBookingFromIndia() {

    try {
      List<InvitedGuestTo> guestList = new ArrayList<InvitedGuestTo>();
      guestList.add(this.invitedAdult);

      BookingTo mockResult = new BookingTo(1L, 0, guestList, BOOKED_AT_INDIA, BOOKING_DATE_INDIA, this.reservationFee);

      Mockito.when(this.ucManageBooking.getBooking(1L)).thenReturn(mockResult);

      MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
          .get("/services/rest/bookingmanagement/v1/booking/1").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()).andReturn();

      String contentAsString = mvcResult.getResponse().getContentAsString();

      assertEquals("{\"id\":1," + "\"modificationCounter\":0,"
          + "\"invitedGuests\":[{\"guestType\":\"Adult\",\"id\":1,\"modificationCounter\":0,\"email\":\"user@gmail.com\"}],"
          + "\"bookedAt\":\"2022-09-09T07:21:40Z\",\"bookingDate\":\"2022-09-10T12:00:00Z\","
          + "\"reservationFee\":{\"amount\":200.00,\"currency\":\"USD\"}}", contentAsString);

    } catch (Exception e) {

      logger.error(e.getMessage());
    }

  }

  /**
   * @Input : When given BookedAt is in Indian Timezone and BookingDate is of German Timezone
   * @Output: Returned Date should be in UTC timezone
   */
  @Test
  public void testGetBookingInGermanyBookedFromIndia() {

    try {
      List<InvitedGuestTo> guestList = new ArrayList<InvitedGuestTo>();
      guestList.add(this.invitedAdult);

      BookingTo mockResult = new BookingTo(2L, 0, guestList, BOOKED_AT_INDIA, BOOKING_DATE_GERMANY,
          this.reservationFee);

      Mockito.when(this.ucManageBooking.getBooking(2L)).thenReturn(mockResult);

      MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
          .get("/services/rest/bookingmanagement/v1/booking/2").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()).andReturn();

      String contentAsString = mvcResult.getResponse().getContentAsString();

      assertEquals("{\"id\":2,\"modificationCounter\":0,"
          + "\"invitedGuests\":[{\"guestType\":\"Adult\",\"id\":1,\"modificationCounter\":0,\"email\":\"user@gmail.com\"}],"
          + "\"bookedAt\":\"2022-09-09T07:21:40Z\",\"bookingDate\":\"2022-09-10T12:30:00Z\","
          + "\"reservationFee\":{\"amount\":200.00,\"currency\":\"USD\"}}", contentAsString);

    } catch (Exception e) {

      logger.error(e.getMessage());
    }

  }

  /**
   * This test case date is to demonstrate inheritance feature.
   *
   * @Input : When given booking with Adult and child as invitedGuests
   * @Output: Returned json should contain property "guestType" with appropriate type Adult or Child
   */
  @Test
  public void testGetBookingwithInvitedGuests() {

    try {

      BookingTo mockResult = new BookingTo(4L, 0, this.invitedGuests, BOOKED_AT_INDIA, BOOKING_DATE_GERMANY,
          this.reservationFee);

      Mockito.when(this.ucManageBooking.getBooking(4L)).thenReturn(mockResult);

      MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
          .get("/services/rest/bookingmanagement/v1/booking/4").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()).andReturn();

      String contentAsString = mvcResult.getResponse().getContentAsString();

      System.out.println("case2:: " + contentAsString);
      assertEquals("{\"id\":4,\"modificationCounter\":0,"
          + "\"invitedGuests\":[{\"guestType\":\"Adult\",\"id\":1,\"modificationCounter\":0,\"email\":\"user@gmail.com\"},"
          + "{\"guestType\":\"Child\",\"id\":2,\"modificationCounter\":0,\"needsSpecialChair\":true}],"
          + "\"bookedAt\":\"2022-09-09T07:21:40Z\",\"bookingDate\":\"2022-09-10T12:30:00Z\","
          + "\"reservationFee\":{\"amount\":200.00,\"currency\":\"USD\"}}", contentAsString);

    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * This is to test create booking functionality.This testcase evaluates input of method.
   *
   * @Input : When given booking with Adult and child as invitedGuests then internally Adult instance should be detected
   *        as adult and Child instance should be detected as child.
   */
  @Test
  public void testCreateBookingWithInvitedGuests() {

    BookingTo mockResult = new BookingTo(4L, 0, this.invitedGuests, BOOKED_AT_INDIA, BOOKING_DATE_GERMANY,
        this.reservationFee);

    List<BookingTo> inputList = new ArrayList<>();
    inputList.add(mockResult);

    try {
      this.mockMvc
          .perform(MockMvcRequestBuilders.post("/services/rest/bookingmanagement/v1/booking")
              .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(inputList)))
          .andExpect(status().isOk());
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    Mockito.verify(this.ucManageBooking).createBooking(this.bookingCaptor.capture());

    List<BookingTo> verifyInputList = this.bookingCaptor.getValue();

    assertEquals(Boolean.TRUE, verifyInputList.get(0).getInvitedGuests().get(0) instanceof InvitedAdultTo
        && verifyInputList.get(0).getInvitedGuests().get(1) instanceof InvitedChildTo);

  }

}
