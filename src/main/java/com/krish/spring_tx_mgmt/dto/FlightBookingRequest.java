package com.krish.spring_tx_mgmt.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.List;

public class FlightBookingRequest {


    private String flightNumber;

    @NotNull
    private LocalDate journeyDate;


    private List<PassengerRequest> passengers;

    @NotNull
    private PaymentRequest payment;

    // getters and setters

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public List<PassengerRequest> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRequest> passengers) {
        this.passengers = passengers;
    }

    public PaymentRequest getPayment() {
        return payment;
    }

    public void setPayment(PaymentRequest payment) {
        this.payment = payment;
    }
}