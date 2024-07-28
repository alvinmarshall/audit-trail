package com.cheise_proj.auditing;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "first_name", length = 100)
    private String firstName;
    @NotBlank
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Email
    @NotBlank
    @Column(name = "email_address")
    private String emailAddress;

    static Customer of(CustomerDto.CreateCustomer customer) {
        return Customer.builder()
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .emailAddress(customer.emailAddress())
                .build();
    }
}
