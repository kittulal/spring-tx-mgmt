package com.krish.spring_tx_mgmt.service;

import com.krish.spring_tx_mgmt.dto.BookingResponse;
import com.krish.spring_tx_mgmt.dto.FlightBookingRequest;
import com.krish.spring_tx_mgmt.dto.PassengerRequest;
import com.krish.spring_tx_mgmt.dto.PaymentRequest;
import com.krish.spring_tx_mgmt.entity.Booking;
import com.krish.spring_tx_mgmt.entity.Passenger;
import com.krish.spring_tx_mgmt.entity.Payment;
import com.krish.spring_tx_mgmt.repo.BookingRepository;
import com.krish.spring_tx_mgmt.repo.PassengerRepository;
import com.krish.spring_tx_mgmt.repo.PaymentRepository;
import com.krish.spring_tx_mgmt.utilis.BookingStatus;
import com.krish.spring_tx_mgmt.utilis.PaymentStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service public class BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final PaymentRepository paymentRepository;
    private final BookingMapper bookingMapper;
    private final PaymentService paymentService;


    public BookingService(BookingRepository bookingRepository,
                          PassengerRepository passengerRepository,
                          PaymentRepository paymentRepository,
                          BookingMapper bookingMapper,
                          PaymentService paymentService) {
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.paymentRepository = paymentRepository;
        this.bookingMapper = bookingMapper;
        this.paymentService = paymentService;
    }

/*
Testing Spring transaction mgmt
Step-1 : Comment the @Transactional annotation to test Spring TX behaviour
Step-2: Goto PaymentService class, under bookingPayment() comment the if condition i.e., line 44
 */
    @Transactional
    public BookingResponse createBooking(FlightBookingRequest request) {

        // 1️⃣ Create Booking
        Booking booking = new Booking();
        booking.setBookingReference(generatePNR());
        booking.setFlightNumber(request.getFlightNumber());
        booking.setJourneyDate(request.getJourneyDate());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepository.save(booking);

        // 2️⃣ Save Passengers
        List<Passenger> passengers = request.getPassengers().stream()
                .map(p -> mapToPassengerEntity(p, booking))
                .collect(Collectors.toList());

        passengerRepository.saveAll(passengers);
        booking.setPassengers(passengers);

        // 3️⃣ Process Payment
        Payment payment = paymentService.bookingPayment(request.getPayment(), booking);
        booking.setPayments(List.of(payment));

        // 4️⃣ Update booking status based on payment
        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
        } else {
            booking.setBookingStatus(BookingStatus.PENDING);
        }

        bookingRepository.save(booking);

        // 5️⃣ Return response
        return bookingMapper.toBookingResponse(booking, payment);
    }

    // ----------------- Helper Methods -----------------

    private Passenger mapToPassengerEntity(PassengerRequest request, Booking booking) {

        Passenger passenger = new Passenger();
        passenger.setBooking(booking);
        passenger.setFirstName(request.getFirstName());
        passenger.setLastName(request.getLastName());
        passenger.setDateOfBirth(request.getDateOfBirth());
        passenger.setGender(request.getGender());
        passenger.setNationality(request.getNationality());
        passenger.setPassportNumber(request.getPassportNumber());
        passenger.setEmail(request.getEmail());
        passenger.setPhoneNumber(request.getPhoneNumber());
        passenger.setSeatNumber(request.getSeatNumber());
        passenger.setPassengerType(request.getPassengerType());
        passenger.setCreatedAt(LocalDateTime.now());

        return passenger;
    }



    private String generatePNR() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }




    public BookingResponse getBooking(String bookingReference) {

        Booking booking = bookingRepository.findById(bookingReference)
                .orElseThrow(() -> new RuntimeException(bookingReference));

        Payment payment = paymentRepository
                .findTopByBooking_BookingReferenceOrderByCreatedAtDesc(bookingReference)
                .orElseThrow(() -> new RuntimeException(bookingReference));

        return bookingMapper.toBookingResponse(booking, payment);
    }
}
