package com.abc.ams.model;

import com.abc.ams.model.constants.AddressType;
import com.abc.ams.model.constants.CountryCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class
Address extends Persistence {
    @Id
    @GeneratedValue
    private UUID addressId;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zipCode;

    private CountryCode countryCode;

    private AddressType addressType;

    @ManyToOne
    private Customer customer;

}
