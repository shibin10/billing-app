package com.shiel.campaignapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shiel.campaignapi.entity.PaymentDetails;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
    Optional<PaymentDetails> findByBookingId(String bookingId);
}

