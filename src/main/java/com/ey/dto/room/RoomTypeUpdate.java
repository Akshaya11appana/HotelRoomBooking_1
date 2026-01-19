package com.ey.dto.room;

import java.math.BigDecimal;
import java.util.Set;

public class RoomTypeUpdate {
  private String name;
  private String description;
  private BigDecimal basePrice;
  private Integer maxOccupancy;
  private Set<String> amenities;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public BigDecimal getBasePrice() { return basePrice; }
  public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
  public Integer getMaxOccupancy() { return maxOccupancy; }
  public void setMaxOccupancy(Integer maxOccupancy) { this.maxOccupancy = maxOccupancy; }
  public Set<String> getAmenities() { return amenities; }
  public void setAmenities(Set<String> amenities) { this.amenities = amenities; }
}
