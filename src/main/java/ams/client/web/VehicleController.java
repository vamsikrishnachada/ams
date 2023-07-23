package ams.client.web;

import ams.client.model.Customer;
import ams.client.model.Vehicle;
import ams.client.services.VehicleService;
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
    public List<Vehicle> findVehicleByMake(@RequestParam("make") String make) {
        return vehicleService.getVehiclesByMake(make);
    }

    @GetMapping(path = "/searchByMakeAndModel", produces = "application/json")
    public List<Vehicle> findVehicleByMakeAndModel(@RequestParam("make") String make, @RequestParam("model") String model) {
        return vehicleService.getVehiclesByMakeAndModel(make, model);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/searchByType", produces = "application/json")
    public List<Vehicle> findVehiclesByType(@RequestParam("type") String type) {
        return vehicleService.getVehiclesByType(type);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/searchByVin", produces = "application/json")
    public Vehicle findVehiclesByVin(@RequestParam("vin") String vin) {
        return vehicleService.getVehiclesByVin(vin);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<String> deleteVehicle(@RequestBody Vehicle vehicle) {
            vehicleService.deleteVehicle(vehicle);
            return ResponseEntity.ok("Vehicle deleted successfully");
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<String> updateVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(vehicle);
        return ResponseEntity.ok("Vehicle updated successfully");
    }



}
