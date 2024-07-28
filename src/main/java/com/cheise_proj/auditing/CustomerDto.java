package com.cheise_proj.auditing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

interface CustomerDto {
    @Builder
    record CreateCustomer(
            @NotBlank @JsonProperty String firstName,
            @NotBlank @JsonProperty String lastName,
            @Email @JsonProperty("email") String emailAddress,
            @JsonProperty Set<CustomerAddress> customerAddress
    ) implements CustomerDto {
    }

    @Builder
    record CustomerAddress(
            @JsonProperty String streetAddress,
            @JsonProperty String city,
            @JsonProperty String stateCode,
            @JsonProperty String country,
            @JsonProperty String zipCode
    ) {
    }

    @Builder
    record GetCustomer(
            @JsonProperty String firstName,
            @JsonProperty String lastName,
            @JsonProperty("email") String emailAddress,
            Set<CustomerAddress> customerAddress
    ) implements CustomerDto {
    }

    @Builder
    record UpdateCustomer(
            @NotBlank @JsonProperty String firstName,
            @NotBlank @JsonProperty String lastName,
            @Email @JsonProperty("email") String emailAddress,
            @JsonProperty Set<CustomerAddress> customerAddress
    ) implements CustomerDto {
    }

    static GetCustomer toGetCustomer(Customer customer) {
        Set<CustomerAddress> customerAddresses = null;
        if (customer.getAddresses() != null) {
            customerAddresses = customer.getAddresses().stream().map(address -> CustomerAddress.builder()
                    .zipCode(address.getZipCode())
                    .city(address.getCity())
                    .country(address.getCountry())
                    .stateCode(address.getStateCode())
                    .streetAddress(address.getStreetAddress())
                    .build()).collect(Collectors.toSet());
        }
        return GetCustomer.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .emailAddress(customer.getEmailAddress())
                .customerAddress(customerAddresses)
                .build();
    }

    static GetCustomer toCustomer(Customer customer) {
        return GetCustomer.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .emailAddress(customer.getEmailAddress())
                .build();
    }
}
