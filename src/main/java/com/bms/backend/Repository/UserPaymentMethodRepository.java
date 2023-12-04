package com.bms.backend.Repository;

import com.bms.backend.entity.Theatre;
import com.bms.backend.entity.UserPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaymentMethodRepository extends JpaRepository<UserPaymentMethod, Long> {
    List<UserPaymentMethod> findUserPaymentMethodsByUserId(Long userId);
}
