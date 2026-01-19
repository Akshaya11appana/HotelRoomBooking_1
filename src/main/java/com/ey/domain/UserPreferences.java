package com.ey.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserPreferences {

    private String bedType;
    private String floorPreference;
    private boolean lateCheckout;
    private boolean smoking;

    public String getBedType() { return bedType; }
    public String getFloorPreference() { return floorPreference; }
    public boolean isLateCheckout() { return lateCheckout; }
    public boolean isSmoking() { return smoking; }


    public void setBedType(String bedType) { this.bedType = bedType; }
    public void setFloorPreference(String floorPreference) { this.floorPreference = floorPreference; }
    public void setLateCheckout(boolean lateCheckout) { this.lateCheckout = lateCheckout; }
    public void setSmoking(boolean smoking) { this.smoking = smoking; }
}
