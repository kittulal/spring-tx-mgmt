package com.krish.spring_tx_mgmt.repo;

import com.krish.spring_tx_mgmt.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    // Get all passengers for a booking
    List<Passenger> findByBooking_BookingReference(String bookingReference);

    // Find passenger by passport
    Optional<Passenger> findByPassportNumber(String passportNumber);

    // Count passengers per booking
    long countByBooking_BookingReference(String bookingReference);
}