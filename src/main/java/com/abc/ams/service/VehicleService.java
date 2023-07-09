package com.abc.ams.service;

import java.util.List;
import java.util.UUID;
import com.abc.ams.VehicleRepository;
import com.abc.ams.model.Vehicle;
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

    public Optional<List<Vehicle>> getVehiclesByMake(String make){
        return Optional.ofNullable(vehicleRepository.findVehiclesByMake(make));
    }
    public Optional<List<Vehicle>> getVehiclesByMakeAndModel(String make, String model){
        return Optional.ofNullable(vehicleRepository.findVehiclesByMakeAndModel(make, model));
    }

    public Optional<List<Vehicle>> getVehiclesByType(String type){
        return Optional.ofNullable(vehicleRepository.findVehiclesByType(type));
    }

    public void deleteVehicle(Vehicle vehicle){
        vehicleRepository.delete(vehicle);
    }

}
