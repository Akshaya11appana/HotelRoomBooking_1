package com.ey.dto.availability;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AvailabilityDtos {

	public static class Range {
		private Long roomTypeId;
		private LocalDate from;
		private LocalDate to;

		public Long getRoomTypeId() {
			return roomTypeId;
		}

		public void setRoomTypeId(Long roomTypeId) {
			this.roomTypeId = roomTypeId;
		}

		public LocalDate getFrom() {
			return from;
		}

		public void setFrom(LocalDate from) {
			this.from = from;
		}

		public LocalDate getTo() {
			return to;
		}

		public void setTo(LocalDate to) {
			this.to = to;
		}
	}

	public static class SearchItem {
		public static class RoomTypeInfo {
			private Long id;
			private String code;
			private String name;
			private Integer maxOccupancy;

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Integer getMaxOccupancy() {
				return maxOccupancy;
			}

			public void setMaxOccupancy(Integer maxOccupancy) {
				this.maxOccupancy = maxOccupancy;
			}
		}

		public static class DateCell {
			private LocalDate date;
			private boolean available;
			private int availableCount;

			public DateCell() {
			}

			public DateCell(LocalDate date, boolean available, int availableCount) {
				this.date = date;
				this.available = available;
				this.availableCount = availableCount;
			}

			public LocalDate getDate() {
				return date;
			}

			public void setDate(LocalDate date) {
				this.date = date;
			}

			public boolean isAvailable() {
				return available;
			}

			public void setAvailable(boolean available) {
				this.available = available;
			}

			public int getAvailableCount() {
				return availableCount;
			}

			public void setAvailableCount(int availableCount) {
				this.availableCount = availableCount;
			}
		}

		private RoomTypeInfo roomType;
		private List<DateCell> dates;
		private BigDecimal basePrice;
		private String currency = "INR";

		public RoomTypeInfo getRoomType() {
			return roomType;
		}

		public void setRoomType(RoomTypeInfo roomType) {
			this.roomType = roomType;
		}

		public List<DateCell> getDates() {
			return dates;
		}

		public void setDates(List<DateCell> dates) {
			this.dates = dates;
		}

		public BigDecimal getBasePrice() {
			return basePrice;
		}

		public void setBasePrice(BigDecimal basePrice) {
			this.basePrice = basePrice;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}
	}

	public static class Calendar {
		public static class Row {
			private LocalDate date;
			private boolean available;
			private int availableCount;
			private BigDecimal price;

			public LocalDate getDate() {
				return date;
			}

			public void setDate(LocalDate date) {
				this.date = date;
			}

			public boolean isAvailable() {
				return available;
			}

			public void setAvailable(boolean available) {
				this.available = available;
			}

			public int getAvailableCount() {
				return availableCount;
			}

			public void setAvailableCount(int availableCount) {
				this.availableCount = availableCount;
			}

			public BigDecimal getPrice() {
				return price;
			}

			public void setPrice(BigDecimal price) {
				this.price = price;
			}
		}

		private Long roomTypeId;
		private List<Row> dates;
		private String currency = "INR";

		public Long getRoomTypeId() {
			return roomTypeId;
		}

		public void setRoomTypeId(Long roomTypeId) {
			this.roomTypeId = roomTypeId;
		}

		public List<Row> getDates() {
			return dates;
		}

		public void setDates(List<Row> dates) {
			this.dates = dates;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}
	}

	public static class HoldRequest {
		private Long roomTypeId;
		private LocalDate checkIn;
		private LocalDate checkOut;
		private Integer guests;

		public Long getRoomTypeId() {
			return roomTypeId;
		}

		public void setRoomTypeId(Long roomTypeId) {
			this.roomTypeId = roomTypeId;
		}

		public LocalDate getCheckIn() {
			return checkIn;
		}

		public void setCheckIn(LocalDate checkIn) {
			this.checkIn = checkIn;
		}

		public LocalDate getCheckOut() {
			return checkOut;
		}

		public void setCheckOut(LocalDate checkOut) {
			this.checkOut = checkOut;
		}

		public Integer getGuests() {
			return guests;
		}

		public void setGuests(Integer guests) {
			this.guests = guests;
		}
	}



	public static class HoldResponse {
		private String holdId;
		private java.time.LocalDateTime expiresAt; 

		public HoldResponse() {
		}

		public HoldResponse(String holdId, java.time.LocalDateTime expiresAt) {
			this.holdId = holdId;
			this.expiresAt = expiresAt;
		}

		public String getHoldId() {
			return holdId;
		}

		public void setHoldId(String holdId) {
			this.holdId = holdId;
		}

		public java.time.LocalDateTime getExpiresAt() {
			return expiresAt;
		}

		public void setExpiresAt(java.time.LocalDateTime expiresAt) {
			this.expiresAt = expiresAt;
		}
	}

}
