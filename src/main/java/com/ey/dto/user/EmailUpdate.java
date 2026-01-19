
package com.ey.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailUpdate {

    @JsonProperty("email")      // JSON --> Java binding guarantee
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
