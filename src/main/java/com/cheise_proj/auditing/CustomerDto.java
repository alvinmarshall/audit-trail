package com.cheise_proj.auditing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;

interface CustomerDto {
    @Builder
    record CreateCustomer(
            @NotBlank @JsonProperty String firstName,
            @NotBlank @JsonProperty String lastName,
            @Email @JsonProperty("email") String emailAddress,
            Set<CustomerAddress> customerAddress
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
}
