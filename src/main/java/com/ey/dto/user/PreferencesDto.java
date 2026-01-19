package com.ey.dto.user;

import com.ey.domain.User;
import com.ey.domain.UserPreferences;

public class PreferencesDto {

    private String bedType;
    private String floorPreference;
    private boolean lateCheckout;
    private boolean smoking;

    public static PreferencesDto from(User u) {
        PreferencesDto dto = new PreferencesDto();

        UserPreferences p = u.getPreferences();
        if (p != null) {
            dto.setBedType(p.getBedType());
            dto.setFloorPreference(p.getFloorPreference());
            dto.setLateCheckout(p.isLateCheckout());
            dto.setSmoking(p.isSmoking());
        }

        return dto;
    }


    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }

    public String getFloorPreference() { return floorPreference; }
    public void setFloorPreference(String floorPreference) { this.floorPreference = floorPreference; }

    public boolean isLateCheckout() { return lateCheckout; }
    public void setLateCheckout(boolean lateCheckout) { this.lateCheckout = lateCheckout; }

    public boolean isSmoking() { return smoking; }
    public void setSmoking(boolean smoking) { this.smoking = smoking; }
}
