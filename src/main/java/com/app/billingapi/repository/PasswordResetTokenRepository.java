package com.app.billingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByToken(String token);

    //void deleteByUserId(Integer userId);
}