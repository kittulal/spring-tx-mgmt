package com.krish.spring_tx_mgmt.controller;

import com.krish.spring_tx_mgmt.dto.BookingResponse;
import com.krish.spring_tx_mgmt.dto.FlightBookingRequest;
import com.krish.spring_tx_mgmt.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightBookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody FlightBookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
}
