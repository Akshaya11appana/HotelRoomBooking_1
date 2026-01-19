package com.ey.dto.room;

import com.ey.domain.RoomType;
import java.math.BigDecimal;
import java.util.Set;

public class RoomTypeDto {
  private Long id;
  private String code;
  private String name;
  private String description;
  private BigDecimal basePrice;
  private String currency = "INR"; // as per your API examples
  private Integer maxOccupancy;
  private Set<String> amenities;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public BigDecimal getBasePrice() { return basePrice; }
  public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }
  public Integer getMaxOccupancy() { return maxOccupancy; }
  public void setMaxOccupancy(Integer maxOccupancy) { this.maxOccupancy = maxOccupancy; }
  public Set<String> getAmenities() { return amenities; }
  public void setAmenities(Set<String> amenities) { this.amenities = amenities; }

  public static RoomTypeDto from(RoomType rt) {
    RoomTypeDto dto = new RoomTypeDto();
    dto.setId(rt.getId());
    dto.setCode(rt.getCode());
    dto.setName(rt.getName());
    dto.setDescription(rt.getDescription());
    dto.setBasePrice(rt.getBasePrice());
    dto.setMaxOccupancy(rt.getMaxOccupancy());
    dto.setAmenities(rt.getAmenities());
    return dto;
  }
}
