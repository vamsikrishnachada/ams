package com.abc.ams.service;

import com.abc.ams.CustomerRepository;
import com.abc.ams.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer searchByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
