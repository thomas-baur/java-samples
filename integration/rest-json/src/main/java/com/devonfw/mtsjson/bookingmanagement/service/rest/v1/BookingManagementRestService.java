package com.devonfw.mtsjson.bookingmanagement.service.rest.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devonfw.mtsjson.bookingmanagement.logic.UcManageBooking;
import com.devonfw.mtsjson.bookingmanagement.service.model.BookingTo;

@RestController
@RequestMapping("/services/rest/bookingmanagement/v1")
public class BookingManagementRestService {

  @Autowired
  private UcManageBooking booking;

  @PostMapping("/booking")
  public List<BookingTo> createBooking(@RequestBody List<BookingTo> bookingDetails) {

    return this.booking.createBooking(bookingDetails);
  }

  @GetMapping("/booking/{id}")
  public BookingTo getBooking(@PathVariable Long id) {

    return this.booking.getBooking(id);
  }
}
