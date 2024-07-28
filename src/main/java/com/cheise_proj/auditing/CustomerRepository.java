package com.cheise_proj.auditing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

interface CustomerRepository extends JpaRepository<Customer, Long> {
    @EntityGraph(attributePaths = "addresses")
    @Override
    Page<Customer> findAll(Pageable pageable);
}
