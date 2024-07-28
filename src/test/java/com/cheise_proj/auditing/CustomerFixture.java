package com.cheise_proj.auditing;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

class CustomerFixture {
    private CustomerFixture() {
    }

    static String createCustomer(ObjectMapper mapper) throws JsonProcessingException {
        CustomerDto.CreateCustomer customerDto = CustomerDto.CreateCustomer.builder()
                .firstName("Debra")
                .lastName("Herman")
                .emailAddress("debra@gmail.com")
                .build();
        return mapper.writeValueAsString(customerDto);
    }
}
