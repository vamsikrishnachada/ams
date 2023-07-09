package com.abc.ams.web;

import com.abc.ams.model.Address;
import com.abc.ams.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @RequestMapping(method = RequestMethod.GET, path = "/searchByzipCode", produces = "application/json")
    public List<Address> findAddressByzipcode(@RequestParam("zipCode") String zipCode) {
        return addressService.searchByzipCode(zipCode);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addAddress(@RequestBody Address address) {
        addressService.addAddress(address);
        return ResponseEntity.ok().build();

    }
}
