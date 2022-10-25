package com.devonfw.mtsjson.bookingmanagement.service.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.money.MonetaryAmount;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingTo implements Serializable {

  private Long id;

  private int modificationCounter;

  private List<InvitedGuestTo> invitedGuests;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "UTC", lenient = OptBoolean.TRUE)
  private ZonedDateTime bookedAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "UTC", lenient = OptBoolean.TRUE)
  private ZonedDateTime bookingDate;

  private MonetaryAmount reservationFee;
}
