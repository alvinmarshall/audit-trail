package com.cheise_proj.auditing;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "first_name")
    private String firstName;
    @NonNull
    @Column(name = "last_name")
    private String lastName;
    @NonNull
    @Column(name = "email_address")
    private String emailAddress;
}
