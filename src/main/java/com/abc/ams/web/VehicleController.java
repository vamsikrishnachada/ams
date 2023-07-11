package com.abc.ams.web;

import com.abc.ams.model.Vehicle;
import com.abc.ams.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok().build();

    }

    @RequestMapping(method = RequestMethod.GET, path = "/searchById", produces = "application/json")
    public Optional<Vehicle> findVehicleByID(@RequestParam("vehicleId") UUID vehicleId) {
        return vehicleService.getVehicleByID(vehicleId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/searchByMake", produces = "application/json")
    public Optional<List<Vehicle>> findVehicleByMake(@RequestParam("make") String make) {
        return vehicleService.getVehiclesByMake(make);
    }

    @GetMapping(path = "/searchByMakeAndModel", produces = "application/json")
    public Optional<List<Vehicle>> findVehicleByMakeAndModel(@RequestParam("make") String make, @RequestParam("model") String model) {
        return vehicleService.getVehiclesByMakeAndModel(make, model);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/searchByType", produces = "application/json")
    public Optional<List<Vehicle>> findVehiclesByType(@RequestParam("type") String type) {
        return vehicleService.getVehiclesByType(type);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/searchByVin", produces = "application/json")
    public Optional<List<Vehicle>> findVehiclesByVin(@RequestParam("vin") String vin) {
        return vehicleService.getVehiclesByVin(vin);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") UUID id) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleByID(id);
        if (vehicle.isPresent()) {
            vehicleService.deleteVehicle(vehicle.get());
            return ResponseEntity.ok("Vehicle deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
