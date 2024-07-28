package com.cheise_proj.auditing;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street_address")
    private String streetAddress;
    private String city;
    @Column(name = "state_code")
    private String stateCode;
    private String country;
    @Column(name = "zip_code")
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    static Address of(CustomerDto.CustomerAddress customerAddress) {
        return Address.builder()
                .city(customerAddress.city())
                .streetAddress(customerAddress.streetAddress())
                .stateCode(customerAddress.stateCode())
                .country(customerAddress.country())
                .zipCode(customerAddress.zipCode())
                .build();
    }

}
