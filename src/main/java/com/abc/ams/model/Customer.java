package com.abc.ams.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Customer extends Persistence {
    @Id
    @GeneratedValue
    private UUID customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;


}
