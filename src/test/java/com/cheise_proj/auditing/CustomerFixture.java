package com.cheise_proj.auditing;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

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

    static String createCustomerWithAddress(ObjectMapper mapper) throws JsonProcessingException {
        CustomerDto.CreateCustomer customerDto = CustomerDto.CreateCustomer.builder()
                .firstName("Troy")
                .lastName("Hahn")
                .emailAddress("troy.hahn@gmail.com")
                .customerAddress(Set.of(CustomerDto.CustomerAddress.builder()
                        .city("Risaberg")
                        .country("USA")
                        .streetAddress("942 Walker Street")
                        .stateCode("WV")
                        .zipCode("88742")
                        .build()))
                .build();
        return mapper.writeValueAsString(customerDto);
    }
}
