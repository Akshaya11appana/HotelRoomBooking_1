
package com.ey.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneUpdate {

    @JsonProperty("phone")      
    private String phone;

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
