package com.bms.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_payment_method")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "payment_method_type")
    private String paymentMethodType;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/[0-9]{2}$", message = "Expiration date should be in the format MM/YY")
    private String expirationDate;

    @Column(name = "cvv")
    private String cvv;
}
