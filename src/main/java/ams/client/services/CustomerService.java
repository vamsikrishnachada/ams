package ams.client.services;

import ams.client.model.Customer;
import ams.client.repositories.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer searchByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
