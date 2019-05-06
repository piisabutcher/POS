package com.springboot.pos.reposity;

import com.springboot.pos.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentReposity extends JpaRepository<Payment,String> {
}
