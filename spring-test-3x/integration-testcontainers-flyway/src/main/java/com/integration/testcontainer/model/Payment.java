package com.integration.testcontainer.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @NonNull
    private Order order;
    @NonNull
    private String creditCardNumber;
}
