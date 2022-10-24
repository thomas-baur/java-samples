package com.devonfw.mtsjson.bookingmanagement.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitedChildTo extends InvitedGuestTo {

  private boolean needsSpecialChair;
}
