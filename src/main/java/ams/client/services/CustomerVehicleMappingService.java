package ams.client.services;

import ams.client.model.Customer;
import ams.client.model.CustomerVehicleMapping;
import ams.client.model.Vehicle;
import ams.client.repositories.CustomerVehicleMappingRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CustomerVehicleMappingService {
    @Autowired
    private CustomerVehicleMappingRepository customerVehicleMappingRepository;
    public List<Vehicle> getVehiclesByCustomer(Customer customer){
        return customerVehicleMappingRepository.findVehiclesByCustomer(customer);
    }
    public List<Customer> getCustomersByVehicle(Vehicle vehicles){
        return customerVehicleMappingRepository.findCustomersByVehicle(vehicles);
    }
    public CustomerVehicleMapping addCustomerVehicleMapping(CustomerVehicleMapping customerVehicleMapping){
        return customerVehicleMappingRepository.save(customerVehicleMapping);
    }
}
