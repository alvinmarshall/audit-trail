package com.cheise_proj.auditing;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EntityListeners(AuditingEntityListener.class)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED, withModifiedFlag = true)
@AuditTable(value = "customers_audit")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "first_name", length = 100)
    private String firstName;
    @NotBlank
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Email
    @NotBlank
    @Column(name = "email_address")
    private String emailAddress;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Address> addresses;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "created_on")
    @CreatedDate
    private Date createdOn;

    @Column(name = "updated_on")
    @LastModifiedDate
    private Date updatedOn;

    static Customer of(CustomerDto.CreateCustomer customer) {
        return Customer.builder()
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .emailAddress(customer.emailAddress())
                .build();
    }

    static Customer of(CustomerDto.UpdateCustomer customer) {
        return Customer.builder()
                .firstName(customer.firstName())
                .lastName(customer.lastName())
                .emailAddress(customer.emailAddress())
                .build();
    }

    void setAddresses(Set<CustomerDto.CustomerAddress> customerAddresses, Customer customer) {
        if (customerAddresses == null) return;
        this.addresses = (this.addresses == null) ? new LinkedHashSet<>() : new LinkedHashSet<>(this.addresses);
        Set<Address> addressSet = customerAddresses.stream().map(customerAddress -> Address.of(customerAddress, customer)).collect(Collectors.toSet());
        this.addresses.addAll(addressSet);
    }
}
