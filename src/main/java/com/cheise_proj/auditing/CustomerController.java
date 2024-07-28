package com.cheise_proj.auditing;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/customers")
class CustomerController {
    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    ResponseEntity<URI> createCustomer(@RequestBody @Valid CustomerDto.CreateCustomer input) {
        Customer customer = customerService.createCustomer(input);
        URI location = UriComponentsBuilder.fromPath("/customers/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
