package com.cheise_proj.auditing;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Address> addresses;

    static Customer of(CustomerDto.CreateCustomer customer) {
        return Customer.builder()
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .emailAddress(customer.emailAddress())
                .build();
    }

    void setAddresses(Set<CustomerDto.CustomerAddress> customerAddresses, Customer customer) {
        if (customerAddresses == null) return;
        this.addresses = (this.addresses == null) ? new LinkedHashSet<>() : this.addresses;
        Set<Address> addressSet = customerAddresses.stream().map(customerAddress -> Address.of(customerAddress, customer)).collect(Collectors.toSet());
        this.addresses.addAll(addressSet);
    }
}
