package com.abc.ams.web;

import com.abc.ams.model.Customer;
import com.abc.ams.model.CustomerVehicleMapping;
import com.abc.ams.model.Vehicle;
import com.abc.ams.service.CustomerService;
import com.abc.ams.service.CustomerVehicleMappingService;
import com.abc.ams.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<List<Customer>> findCustomersByVehicleID(@RequestParam("make") String make, @RequestParam("model") String model){
        return Optional.ofNullable(customerVehicleMappingService.getCustomersByVehicle(vehicleService.getVehiclesByMakeAndModel(make, model)));
    }

    @GetMapping(path="/searchCustomersByVehicleType", produces = "application/json")
    public Optional<List<Customer>> findCustomersByVehicleID(@RequestParam("type") String type){
        return Optional.ofNullable(customerVehicleMappingService.getCustomersByVehicle(vehicleService.getVehiclesByType(type)));
    }

    @GetMapping(path="/searchCustomersByVehicleVin", produces = "application/json")
    public Optional<List<Customer>> findCustomersByVehicleVin(@RequestParam("vin") String vin){
        return Optional.ofNullable(customerVehicleMappingService.getCustomersByVehicle(vehicleService.getVehiclesByVin(vin)));
    }
}
