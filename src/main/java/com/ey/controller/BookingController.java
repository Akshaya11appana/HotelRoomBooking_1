
package com.ey.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.booking.BookingCreateRequest;
import com.ey.dto.booking.BookingNoteRequest;
import com.ey.dto.booking.BookingResponse;
import com.ey.dto.booking.CancelResponse;
import com.ey.service.BookingService;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	public ResponseEntity<BookingResponse> create(@RequestBody BookingCreateRequest req) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(req));
	}

	@GetMapping("/{id}")
	public BookingResponse get(@PathVariable Long id) {
		return bookingService.get(id);
	}

	@GetMapping("/me")
	public List<BookingResponse> myBookings() {
		return bookingService.listMine();
	}

	@GetMapping
	public List<BookingResponse> listAll() {
		return bookingService.listAll();
	}

	@DeleteMapping("/{id}")
	public CancelResponse cancel(@PathVariable Long id) {
		return bookingService.cancel(id);
	}

	@PostMapping("/{id}/check-in")
	public BookingResponse checkIn(@PathVariable Long id) {
		return bookingService.checkIn(id);
	}

	@PostMapping("/{id}/check-out")
	public BookingResponse checkOut(@PathVariable Long id) {
		return bookingService.checkOut(id);
	}

	@PostMapping("/{id}/notes")
	public Map<String, Object> addNote(@PathVariable Long id, @RequestBody BookingNoteRequest req) {
		return bookingService.addNote(id, req);
	}

	@GetMapping("/{id}/notes")
	public List<Map<String, Object>> notes(@PathVariable Long id) {
		return bookingService.listNotes(id);
	}
}
