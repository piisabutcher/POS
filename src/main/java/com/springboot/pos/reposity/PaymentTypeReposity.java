package com.springboot.pos.reposity;

import com.springboot.pos.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeReposity extends JpaRepository<PaymentType,String> {
}