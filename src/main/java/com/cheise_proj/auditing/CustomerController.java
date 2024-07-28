package com.cheise_proj.auditing;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    ResponseEntity<Object> index(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Page<Customer> customerPage = customerService.getCustomers(PageRequest.of(page, size));
        List<CustomerDto.GetCustomer> customerList = customerPage.getContent().stream().map(CustomerDto::toGetCustomer).toList();
        Map<String, Object> customers = Map.of("customers", customerList, "total", customerPage.getTotalElements());
        return ResponseEntity.ok(customers);
    }


}
