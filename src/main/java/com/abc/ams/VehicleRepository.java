package com.abc.ams;

import com.abc.ams.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, UUID> {
 List<Vehicle> findVehiclesByMake(String make);
 List<Vehicle> findVehiclesByMakeAndModel(String make,String model);

 List<Vehicle> findVehiclesByType(String type);

 List<Vehicle> findVehiclesByVin(String vin);
}
