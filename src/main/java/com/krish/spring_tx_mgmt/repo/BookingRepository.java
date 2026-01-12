package com.krish.spring_tx_mgmt.repo;

import com.krish.spring_tx_mgmt.entity.Booking;
import com.krish.spring_tx_mgmt.utilis.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    // Find booking by reference (PNR)
    Booking findByBookingReference(String bookingReference);

    // Find bookings by flight and journey date
    List<Booking> findByFlightNumberAndJourneyDate(
            String flightNumber,
            LocalDate journeyDate
    );

    // Find bookings by status
    List<Booking> findByBookingStatus(BookingStatus status);

    // Check if booking exists
    boolean existsByBookingReference(String bookingReference);
}