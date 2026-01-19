package com.ey.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "room_types")
public class RoomType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 20)
  private String code;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
  private BigDecimal basePrice;

  @Column(name = "max_occupancy", nullable = false)
  private Integer maxOccupancy;

  @ElementCollection
  @CollectionTable(name = "room_type_amenities", joinColumns = @JoinColumn(name = "room_type_id"))
  @Column(name = "amenity", nullable = false)
  private Set<String> amenities = new LinkedHashSet<>();

  @Column(nullable = false)
  private boolean archived = false;

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

  public Integer getMaxOccupancy() { return maxOccupancy; }
  public void setMaxOccupancy(Integer maxOccupancy) { this.maxOccupancy = maxOccupancy; }

  public Set<String> getAmenities() { return amenities; }
  public void setAmenities(Set<String> amenities) { this.amenities = amenities; }

  public boolean isArchived() { return archived; }
  public void setArchived(boolean archived) { this.archived = archived; }
}
