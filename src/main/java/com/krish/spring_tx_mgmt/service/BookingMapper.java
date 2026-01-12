package com.krish.spring_tx_mgmt.service;

import com.krish.spring_tx_mgmt.dto.BookingResponse;
import com.krish.spring_tx_mgmt.dto.PassengerResponse;
import com.krish.spring_tx_mgmt.dto.PaymentResponse;
import com.krish.spring_tx_mgmt.entity.Booking;
import com.krish.spring_tx_mgmt.entity.Passenger;
import com.krish.spring_tx_mgmt.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public BookingResponse toBookingResponse(Booking booking, Payment latestPayment) {

        BookingResponse response = new BookingResponse();
        response.setBookingReference(booking.getBookingReference());
        response.setFlightNumber(booking.getFlightNumber());
        response.setJourneyDate(booking.getJourneyDate());
        response.setBookingStatus(booking.getBookingStatus());
        response.setBookedAt(booking.getCreatedAt());

        response.setPassengers(mapPassengers(booking.getPassengers()));
        response.setPayment(mapPayment(latestPayment));

        return response;
    }

    private List<PassengerResponse> mapPassengers(List<Passenger> passengers) {
        return passengers.stream()
                .map(this::mapPassenger)
                .collect(Collectors.toList());
    }

    private PassengerResponse mapPassenger(Passenger passenger) {
        PassengerResponse response = new PassengerResponse();
        response.setFirstName(passenger.getFirstName());
        response.setLastName(passenger.getLastName());
        response.setSeatNumber(passenger.getSeatNumber());
        response.setPassengerType(passenger.getPassengerType());
        return response;
    }

    private PaymentResponse mapPayment(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setAmount(payment.getAmount());
        response.setCurrency(payment.getCurrency());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setTransactionId(payment.getTransactionId());
        response.setPaidAt(payment.getPaidAt());
        return response;
    }
}