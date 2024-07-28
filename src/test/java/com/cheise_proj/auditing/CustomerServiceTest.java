package com.cheise_proj.auditing;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Mockito.verify(customerRepository, Mockito.atMostOnce()).save(customerArgumentCaptor.capture());
        Customer customer = customerArgumentCaptor.getValue();
        assertNotNull(customer);
        assertEquals("Claribel", customer.getFirstName());
        assertEquals("Zieme", customer.getLastName());
        assertEquals("claribel.zieme@gmail.com", customer.getEmailAddress());
    }

    @Test
    void createCustomerWithAddress() {
        CustomerDto.CreateCustomer customerDto = CustomerDto.CreateCustomer.builder()
                .firstName("Claribel")
                .lastName("Zieme")
                .emailAddress("claribel.zieme@gmail.com")
                .customerAddress(Set.of(CustomerDto.CustomerAddress.builder()
                        .city("Risaberg")
                        .country("USA")
                        .streetAddress("942 Walker Street")
                        .stateCode("WV")
                        .zipCode("88742")
                        .build()))
                .build();
        sut.createCustomer(customerDto);
        Mockito.verify(customerRepository, Mockito.atMostOnce()).save(customerArgumentCaptor.capture());
        Customer customer = customerArgumentCaptor.getValue();
        assertNotNull(customer);
        assertEquals(1, customer.getAddresses().size());
    }

    @Test
    void getCustomers() {
        List<Customer> customerList = List.of(Customer.builder().id(1L).build());
        Mockito.when(customerRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(customerList));
        Page<Customer> customerPage = sut.getCustomers(Pageable.ofSize(1));
        assertNotNull(customerPage);
        assertEquals(1, customerPage.getTotalElements());
        assertEquals(1, customerPage.getContent().size());
    }

    @Test
    void getCustomer() {
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(Customer.builder().id(1L).build()));
        Customer customer = sut.getCustomer(1L);
        assertNotNull(customer);
    }

    @Test
    void getCustomer_throw_if_customer_not_found() {
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> sut.getCustomer(1L)
        );
        assertEquals("Customer with id 1 not found", exception.getMessage());
    }
}