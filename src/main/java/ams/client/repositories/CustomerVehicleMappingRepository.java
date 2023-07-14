package ams.client.repositories;

import ams.client.model.Customer;
import ams.client.model.CustomerVehicleMapping;
import ams.client.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerVehicleMappingRepository extends CrudRepository<CustomerVehicleMapping, UUID> {
List<Vehicle> findVehiclesByCustomer(Customer customer);
List<Customer> findCustomersByVehicle(Vehicle vehicle);

}
