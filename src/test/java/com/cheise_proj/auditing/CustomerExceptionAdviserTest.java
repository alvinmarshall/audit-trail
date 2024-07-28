package com.cheise_proj.auditing;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CustomerExceptionAdviserTest {
    @InjectMocks
    private CustomerExceptionAdviser sut;

    @Test
    void resourceNotFoundException() {
        ProblemDetail notFound = sut.resourceNotFoundException(new EntityNotFoundException("not found"));
        assertNotNull(notFound);
    }
}