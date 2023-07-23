package ams.client.web;

import ams.client.model.Customer;
import ams.client.model.CustomerVehicleMapping;
import ams.client.model.Vehicle;
import ams.client.services.CustomerVehicleMappingService;
import ams.client.services.CustomerService;
import ams.client.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/CustomerVehicleMapping")
public class CustomerVehicleMappingController {

    @Autowired
    private CustomerVehicleMappingService customerVehicleMappingService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VehicleService vehicleService;


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addCustomerVehicleMapping(@RequestBody CustomerVehicleMapping customerVehicleMapping){
        customerVehicleMappingService.addCustomerVehicleMapping(customerVehicleMapping);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/searchVehiclesByCustomerEmail/{email}", produces = "application/json")
    public Optional<List<Vehicle>> findVehiclesByCustomerEmail(@RequestParam("email") String email){
        return Optional.ofNullable(customerVehicleMappingService.getVehiclesByCustomer(customerService.searchByEmail(email)));
    }

    @GetMapping(path="/searchCustomersByVehicleMakeAndModel", produces = "application/json")
    public List<Customer> findCustomersByVehicleID(@RequestParam("make") String make, @RequestParam("model") String model){
        return vehicleService.getVehiclesByMakeAndModel(make, model)
                .stream()
                .flatMap(vehicle -> customerVehicleMappingService.getCustomersByVehicle(vehicle).stream())
                .collect(Collectors.toList());

    }

    @GetMapping(path="/searchCustomersByVehicleType", produces = "application/json")
    public List<Customer> findCustomersByVehicleID(@RequestParam("type") String type){
        return vehicleService.getVehiclesByType(type)
                .stream()
                .flatMap(vehicle -> customerVehicleMappingService.getCustomersByVehicle(vehicle).stream())
                .collect(Collectors.toList());
    }

    @GetMapping(path="/searchCustomersByVehicleVin", produces = "application/json")
    public List<Customer> findCustomersByVehicleVin(@RequestParam("vin") String vin){
        Vehicle vehicle = vehicleService.getVehiclesByVin(vin);
        return customerVehicleMappingService.getCustomersByVehicle(vehicle).stream().collect(Collectors.toList());
    }
}
