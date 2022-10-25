package com.devonfw.mtsjson.bookingmanagement.service.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(use = Id.NAME, property = "guestType", visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = InvitedAdultTo.class, name = "Adult"),
@JsonSubTypes.Type(value = InvitedChildTo.class, name = "Child") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitedGuestTo {

  private Long id;

  private int modificationCounter;
}
