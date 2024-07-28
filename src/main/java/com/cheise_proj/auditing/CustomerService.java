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

    Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id %d not found".formatted(id)));
    }

    Customer getCustomerWithAddress(Long id) {
        return customerRepository.findByIdWithAddress(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id %d not found".formatted(id)));
    }

    Customer updateCustomer(Long customerId, CustomerDto.UpdateCustomer updateCustomer) {
        Customer customer = getCustomerWithAddress(customerId);
        Customer newCustomer = Customer.of(updateCustomer);
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setEmailAddress(newCustomer.getEmailAddress());
        customer.setAddresses(updateCustomer.customerAddress(), customer);
        return customerRepository.save(customer);
    }

}
