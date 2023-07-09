package com.abc.ams.web;

import com.abc.ams.model.Customer;
import com.abc.ams.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @RequestMapping(method = RequestMethod.GET, path = "/searchByEmail", produces = "application/json")
    public Customer findCustomerByEmail(@RequestParam("email") String email) {
        return customerService.searchByEmail(email);

    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.ok().build();

    }
}
