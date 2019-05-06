package com.springboot.pos.dao;

import com.springboot.pos.entity.PaymentType;
import com.springboot.pos.reposity.PaymentTypeReposity;
import org.springframework.data.jpa.repository.Query;

public interface IPaymentTypeDAO extends PaymentTypeReposity {
}
