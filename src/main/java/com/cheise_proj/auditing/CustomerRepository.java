package com.cheise_proj.auditing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface CustomerRepository extends JpaRepository<Customer, Long> {
    @EntityGraph(attributePaths = "addresses")
    @Override
    Page<Customer> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "addresses")
    @Query("select c from Customer c where c.id = :id")
    Optional<Customer> findByIdWithAddress(Long id);
}
