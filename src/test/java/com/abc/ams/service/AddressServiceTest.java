package com.abc.ams.service;
import com.abc.ams.AddressRepository;
import com.abc.ams.model.Address;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    AddressRepository addressRepository;

    AddressService addressService;

    @BeforeEach
    public void init() {
        addressService = new AddressService();
        addressService.setAddressRepository(addressRepository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void searchByzipCode() {
        Mockito.when(addressRepository.findAddressByzipCode(Mockito.anyString())).thenReturn(mockAddress());
        Address address = addressService.searchByzipCode("zipCode");
        assertNotNull(address);
        assertEquals("75022", address.getZipCode());

    }

    private Address mockAddress() {
        Address address = new Address();
        address.setAddress1("6800 cache court");
        address.setZipCode("75022");
        return address;
    }

    @Test
    void addAddress() {
    }

}