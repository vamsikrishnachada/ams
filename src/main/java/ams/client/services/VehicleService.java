package ams.client.services;

import java.util.List;
import java.util.UUID;

import ams.client.model.Vehicle;
import ams.client.repositories.VehicleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public void addVehicle(Vehicle vehicle){
        vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> getVehicleByID(UUID vehicleId){
        return vehicleRepository.findById(vehicleId);
    }

    public List<Vehicle> getVehiclesByMake(String make){
        return vehicleRepository.findVehiclesByMake(make);
    }
    public List<Vehicle> getVehiclesByMakeAndModel(String make, String model){
        return vehicleRepository.findVehiclesByMakeAndModel(make, model);
    }

    public List<Vehicle> getVehiclesByType(String type){
        return vehicleRepository.findVehiclesByType(type);
    }

    public List<Vehicle> getVehiclesByVin(String vin){
        return vehicleRepository.findVehiclesByVin(vin);
    }

    public void deleteVehicle(Vehicle vehicle){
        vehicleRepository.delete(vehicle);
    }

}
