package com.cheise_proj.auditing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    private CustomerService sut;
    @Mock
    private CustomerRepository customerRepository;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCustomer() {
        CustomerDto.CreateCustomer customerDto = CustomerDto.CreateCustomer.builder()
                .firstName("Claribel")
                .lastName("Zieme")
                .emailAddress("claribel.zieme@gmail.com")
                .build();
        sut.createCustomer(customerDto);
        Mockito.verify(customerRepository,Mockito.atMostOnce()).save(customerArgumentCaptor.capture());
        Customer customer = customerArgumentCaptor.getValue();
        assertNotNull(customer);
        assertEquals("Claribel", customer.getFirstName());
        assertEquals("Zieme", customer.getLastName());
        assertEquals("claribel.zieme@gmail.com", customer.getEmailAddress());
    }
}