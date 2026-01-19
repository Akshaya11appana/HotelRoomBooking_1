package com.ey.dto.user;

public class PreferencesDto {
  private String bedType;
  private Boolean smoking;
  private Boolean lateCheckout;
  private String floorPreference;

  public String getBedType() { return bedType; }
  public void setBedType(String bedType) { this.bedType = bedType; }

  public Boolean getSmoking() { return smoking; }
  public void setSmoking(Boolean smoking) { this.smoking = smoking; }

  public Boolean getLateCheckout() { return lateCheckout; }
  public void setLateCheckout(Boolean lateCheckout) { this.lateCheckout = lateCheckout; }

  public String getFloorPreference() { return floorPreference; }
  public void setFloorPreference(String floorPreference) { this.floorPreference = floorPreference; }
}
