package com.devonfw.mtsjson.bookingmanagement.service.model;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitedAdultTo extends InvitedGuestTo {

  private String email;
}
