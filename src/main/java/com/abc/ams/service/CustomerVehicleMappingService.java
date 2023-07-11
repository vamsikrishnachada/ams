package com.abc.ams.service;

import com.abc.ams.model.Customer;
import com.abc.ams.model.CustomerVehicleMapping;
import com.abc.ams.CustomerVehicleMappingRepository;
import com.abc.ams.model.Vehicle;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class CustomerVehicleMappingService {
    @Autowired
    private CustomerVehicleMappingRepository customerVehicleMappingRepository;
    public List<Vehicle> getVehiclesByCustomer(Customer customer){
        return customerVehicleMappingRepository.findVehiclesByCustomer(customer);
    }
    public List<Customer> getCustomersByVehicle(Optional<List<Vehicle>> vehicle){
        return customerVehicleMappingRepository.findCustomersByVehicle(vehicle);
    }
    public CustomerVehicleMapping addCustomerVehicleMapping(CustomerVehicleMapping customerVehicleMapping){
        return customerVehicleMappingRepository.save(customerVehicleMapping);
    }
}
