package com.ey.service;

import com.ey.domain.Availability;
import com.ey.domain.AvailabilityHold;
import com.ey.domain.RoomType;
import com.ey.dto.availability.AvailabilityDtos.HoldRequest;
import com.ey.dto.availability.AvailabilityDtos.HoldResponse;
import com.ey.repo.AvailabilityHoldRepo;
import com.ey.repo.AvailabilityRepo;
import com.ey.repo.RoomTypeRepo;
import com.ey.security.CurrentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AvailabilityServiceCreateHoldTest {

    private AvailabilityRepo availabilityRepo;
    private AvailabilityHoldRepo holdRepo;
    private RoomTypeRepo roomTypeRepo;
    private CurrentUser currentUser;
    private AvailabilityService service;

    @BeforeEach
    void setup() {
        availabilityRepo = mock(AvailabilityRepo.class);
        holdRepo = mock(AvailabilityHoldRepo.class);
        roomTypeRepo = mock(RoomTypeRepo.class);
        currentUser = mock(CurrentUser.class);
        service = new AvailabilityService(availabilityRepo, holdRepo, roomTypeRepo, currentUser);
    }

    @Test
    void createHold_succeeds_when_capacity_is_available() {
        
        RoomType rt = new RoomType();
        rt.setId(1L);
        when(roomTypeRepo.findById(1L)).thenReturn(Optional.of(rt));

       
        Availability a1 = new Availability();
        a1.setDate(LocalDate.parse("2026-02-01"));
        a1.setAvailableCount(3);
        a1.setPrice(new BigDecimal("4500"));
        when(availabilityRepo.findByRoomType_IdAndDateBetween(eq(1L), any(), any()))
                .thenReturn(List.of(a1));

        
        when(holdRepo.findByRoomTypeAndExpiresAtAfter(eq(rt), any(LocalDateTime.class)))
                .thenReturn(List.of());

        HoldRequest req = new HoldRequest();
        req.setRoomTypeId(1L);
        req.setCheckIn(LocalDate.parse("2026-02-01"));
        req.setCheckOut(LocalDate.parse("2026-02-02"));
        req.setGuests(2);

        HoldResponse res = service.createHold(req);

        assertThat(res.getHoldId()).isNotBlank();
        assertThat(res.getExpiresAt()).isAfter(LocalDateTime.now().minusSeconds(1));
        verify(holdRepo).save(any(AvailabilityHold.class));
    }
}
