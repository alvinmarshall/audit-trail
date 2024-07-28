package com.cheise_proj.auditing;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class CustomerService {
    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    Customer createCustomer(CustomerDto.CreateCustomer customer) {
        Customer newCustomer = Customer.of(customer);
        newCustomer.setAddresses(customer.customerAddress(), newCustomer);
        return customerRepository.save(newCustomer);
    }

    Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id %d not found".formatted(id)));
    }

}
