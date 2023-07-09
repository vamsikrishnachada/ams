package com.abc.ams.service;

import com.abc.ams.AddressRepository;
import com.abc.ams.model.Address;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> searchByzipCode(String email) {
        return addressRepository.findAddressByzipCode(email);
    }

    public void addAddress(Address address) {
        addressRepository.save(address);
    }
}