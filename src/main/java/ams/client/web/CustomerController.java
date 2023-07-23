package ams.client.web;

import ams.client.model.Customer;
import ams.client.services.CustomerService;
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

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity deleteCustomer(@RequestBody Customer customer) {
        customerService.deleteCustomer(customer);
        return ResponseEntity.ok().build();
    }
}
