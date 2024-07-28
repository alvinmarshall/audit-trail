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
}
