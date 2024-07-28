package com.cheise_proj.auditing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

interface CustomerDto {
    @Builder
    record CreateCustomer(
            @NotBlank @JsonProperty String firstName,
            @NotBlank @JsonProperty String lastName,
            @Email @JsonProperty("email") String emailAddress
    ) implements CustomerDto {
    }
}
