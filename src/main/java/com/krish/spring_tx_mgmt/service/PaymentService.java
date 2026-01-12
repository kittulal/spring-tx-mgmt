package com.krish.spring_tx_mgmt.service;

import com.krish.spring_tx_mgmt.dto.PaymentRequest;
import com.krish.spring_tx_mgmt.entity.Booking;
import com.krish.spring_tx_mgmt.entity.Payment;
import com.krish.spring_tx_mgmt.repo.PaymentRepository;
import com.krish.spring_tx_mgmt.utilis.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment mapToPaymentEntity(PaymentRequest request, Booking booking) {

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setPaymentMethod(request.getPaymentMethod());

        // Simulated payment gateway response
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(generateTransactionId());
        payment.setPaymentGateway("INTERNAL_GATEWAY");
        payment.setPaidAt(LocalDateTime.now());
        payment.setCreatedAt(LocalDateTime.now());

        return payment;
    }

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    public Payment bookingPayment(PaymentRequest request, Booking booking) {
        // 3️⃣ Process Payment
        Payment payment = mapToPaymentEntity(request, booking);
//        if(payment.getPaymentGateway().equals("INTERNAL_GATEWAY")) {
//            throw new RuntimeException("Booking payment failed!");
//        }
        paymentRepository.save(payment);
        return payment;
    }
}
