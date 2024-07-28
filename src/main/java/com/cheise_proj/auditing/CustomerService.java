package com.cheise_proj.auditing;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuditReader auditReader;


    CustomerService(CustomerRepository customerRepository, AuditReader auditReader) {
        this.customerRepository = customerRepository;
        this.auditReader = auditReader;
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

    void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    List<Map<String, Object>> getRevisions(Long id, boolean fetchChanges) {
        AuditQuery auditQuery;
        if (fetchChanges) {
            auditQuery = auditReader.createQuery()
                    .forRevisionsOfEntityWithChanges(Customer.class, true);
        } else {
            auditQuery = auditReader.createQuery()
                    .forRevisionsOfEntity(Customer.class, true);
        }
        auditQuery.add(AuditEntity.id().eq(id));
        @SuppressWarnings("unchecked") List<Object> results = auditQuery.getResultList();
        return AuditRevisionEntity.toRevisionResults(results);
    }
}
