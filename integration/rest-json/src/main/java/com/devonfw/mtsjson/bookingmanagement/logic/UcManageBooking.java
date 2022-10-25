package com.devonfw.mtsjson.bookingmanagement.logic;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.devonfw.mtsjson.bookingmanagement.common.exception.NotImplementedException;
import com.devonfw.mtsjson.bookingmanagement.service.model.BookingTo;

@Component
public class UcManageBooking {

  public List<BookingTo> createBooking(List<BookingTo> bookingDetails) {

    throw new NotImplementedException("Logic Not implemented");

  }

  public BookingTo getBooking(@PathVariable Long id) {

    throw new NotImplementedException("Logic Not implemented");
  }
}
