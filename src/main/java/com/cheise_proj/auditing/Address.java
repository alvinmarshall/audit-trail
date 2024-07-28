package com.cheise_proj.auditing;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;

    static Address of(CustomerDto.CustomerAddress customerAddress, Customer customer) {
        return Address.builder()
                .city(customerAddress.city())
                .streetAddress(customerAddress.streetAddress())
                .stateCode(customerAddress.stateCode())
                .country(customerAddress.country())
                .zipCode(customerAddress.zipCode())
                .customer(customer)
                .build();
    }

}
