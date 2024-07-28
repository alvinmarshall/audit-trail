package com.cheise_proj.auditing;

import org.springframework.stereotype.Service;

@Service
class CustomerService {
    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    Customer createCustomer(CustomerDto.CreateCustomer customer) {
        Customer newCustomer = Customer.of(customer);
        return customerRepository.save(newCustomer);
    }
}
