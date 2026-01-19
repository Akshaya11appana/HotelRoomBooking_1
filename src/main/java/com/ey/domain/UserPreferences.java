package com.ey.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserPreferences {

  private Boolean smoking;
  private String bedType;
  private Boolean lateCheckout;
  private String floorPreference;

  public UserPreferences() {
  }

  public UserPreferences(Boolean smoking, String bedType, Boolean lateCheckout, String floorPreference) {
    this.smoking = smoking;
    this.bedType = bedType;
    this.lateCheckout = lateCheckout;
    this.floorPreference = floorPreference;
  }

  public Boolean getSmoking() {
    return smoking;
  }

  public void setSmoking(Boolean smoking) {
    this.smoking = smoking;
  }

  public String getBedType() {
    return bedType;
  }

  public void setBedType(String bedType) {
    this.bedType = bedType;
  }

  public Boolean getLateCheckout() {
    return lateCheckout;
  }

  public void setLateCheckout(Boolean lateCheckout) {
    this.lateCheckout = lateCheckout;
  }

  public String getFloorPreference() {
    return floorPreference;
  }

  public void setFloorPreference(String floorPreference) {
    this.floorPreference = floorPreference;
  }
}
