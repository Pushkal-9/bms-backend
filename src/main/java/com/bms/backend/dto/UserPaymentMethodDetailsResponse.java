package com.bms.backend.dto;

import com.bms.backend.entity.UserPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPaymentMethodDetailsResponse {

    private List<UserPaymentMethod> userPaymentMethodList;
}
