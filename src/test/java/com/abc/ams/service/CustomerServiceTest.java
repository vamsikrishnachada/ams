package com.abc.ams.service;

import com.abc.ams.CustomerRepository;
import com.abc.ams.model.Customer;
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
public class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    public void init() {
        customerService = new CustomerService();
        customerService.setCustomerRepository(customerRepository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void searchByEmail() {
        Mockito.when(customerRepository.findCustomerByEmail(Mockito.anyString())).thenReturn(mockCustomer());
        Customer customer = customerService.searchByEmail("email");
        assertNotNull(customer);
        assertEquals("abc", customer.getFirstName());
    }

    private Customer mockCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("abc");
        customer.setEmail("a@a.com");
        return customer;
    }

    @Test
    void addCustomer() {
    }
}