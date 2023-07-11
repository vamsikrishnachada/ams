package com.abc.ams.service;


import com.abc.ams.CustomerVehicleMappingRepository;
import com.abc.ams.model.Customer;
import com.abc.ams.model.CustomerVehicleMapping;
import com.abc.ams.model.Vehicle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CustomerVehicleMappingServiceTest {

    @Mock
    CustomerVehicleMappingRepository customerVehicleMappingRepository;

    CustomerVehicleMappingService customerVehicleMappingService;

    @BeforeEach
    public void setUp() {
        customerVehicleMappingService = new CustomerVehicleMappingService();
        customerVehicleMappingService.setCustomerVehicleMappingRepository(customerVehicleMappingRepository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getVehiclesByCustomer() {
        Vehicle vehicle = mockVehicle();

        Customer customer = mockCustomer();

        CustomerVehicleMapping customerVehicleMapping = new CustomerVehicleMapping();
        customerVehicleMapping.setCustomer(customer);
        customerVehicleMapping.setVehicle(vehicle);

        Mockito.when(customerVehicleMappingRepository.findVehiclesByCustomer(customer)).thenReturn(Collections.singletonList(vehicle));

        List<Vehicle> vehicles = customerVehicleMappingService.getVehiclesByCustomer(customer);
        assertNotNull(vehicles);
        assertEquals(1, vehicles.size());
        assertEquals("Toyota", vehicles.get(0).getMake());
        assertEquals("Camry", vehicles.get(0).getModel());
    }

    @Test
    public void getCustomersByVehicle() {
        Customer customer = mockCustomer();
        Vehicle vehicle = mockVehicle();

        CustomerVehicleMapping customerVehicleMapping = new CustomerVehicleMapping();
        customerVehicleMapping.setCustomer(customer);
        customerVehicleMapping.setVehicle(vehicle);

        Mockito.when(customerVehicleMappingRepository.findCustomersByVehicle(Optional.of(Collections.singletonList(vehicle)))).thenReturn(Collections.singletonList(customer));

        List<Customer> customers = customerVehicleMappingService.getCustomersByVehicle(Optional.of(Collections.singletonList(vehicle)));
        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
    }

    @Test
    public void addCustomerVehicleMapping() {
        CustomerVehicleMapping customerVehicleMapping = new CustomerVehicleMapping();
        Mockito.when(customerVehicleMappingRepository.save(customerVehicleMapping)).thenReturn(customerVehicleMapping);

        CustomerVehicleMapping savedMapping = customerVehicleMappingService.addCustomerVehicleMapping(customerVehicleMapping);
        assertNotNull(savedMapping);
        assertEquals(customerVehicleMapping, savedMapping);
        Mockito.verify(customerVehicleMappingRepository, times(1)).save(customerVehicleMapping);
    }

    private Customer mockCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setEmail("a@a.com");
        return customer;
    }

    private Vehicle mockVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Camry");
        vehicle.setVin("123");
        vehicle.setType("SUV");
        return vehicle;
    }
}

