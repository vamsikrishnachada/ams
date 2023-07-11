package com.abc.ams;

import com.abc.ams.model.Customer;
import com.abc.ams.model.CustomerVehicleMapping;
import com.abc.ams.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerVehicleMappingRepository extends CrudRepository<CustomerVehicleMapping, UUID> {
List<Vehicle> findVehiclesByCustomer(Customer customer);
List<Customer> findCustomersByVehicle(Optional<List<Vehicle>> vehicle);

}
