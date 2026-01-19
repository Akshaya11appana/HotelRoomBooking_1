
package com.ey.repo;

import com.ey.domain.BookingNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingNoteRepo extends JpaRepository<BookingNote, Long> {

    List<BookingNote> findByBooking_IdOrderByCreatedAtAsc(Long bookingId);
}
