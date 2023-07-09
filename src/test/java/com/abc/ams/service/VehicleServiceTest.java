package com.abc.ams.service;

import com.abc.ams.VehicleRepository;
import com.abc.ams.model.Vehicle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @Mock
    VehicleRepository vehicleRepository;

    VehicleService vehicleService;

    @BeforeEach
    public void init() {
        vehicleService = new VehicleService();
        vehicleService.setVehicleRepository(vehicleRepository);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void addVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicleService.addVehicle(vehicle);
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(vehicle);
    }

    @Test
    public void getVehicleByID() {
        UUID vehicleId = UUID.randomUUID();
        Vehicle mockVehicle = mockVehicle();
        Mockito.when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(mockVehicle));

        Optional<Vehicle> vehicle = vehicleService.getVehicleByID(vehicleId);
        assertNotNull(vehicle);
        assertTrue(vehicle.isPresent());
        assertEquals("Toyota", vehicle.get().getMake());
        assertEquals("Camry", vehicle.get().getModel());
    }

    @Test
    public void getVehiclesByMake() {
        String make = "Toyota";
        Vehicle mockVehicle = mockVehicle();
        Mockito.when(vehicleRepository.findVehiclesByMake(make)).thenReturn(List.of(mockVehicle));

        Optional<List<Vehicle>> vehicles = vehicleService.getVehiclesByMake(make);
        assertNotNull(vehicles);
        assertTrue(vehicles.isPresent());
        assertEquals(1, vehicles.get().size());
        assertEquals("Toyota", vehicles.get().get(0).getMake());
        assertEquals("Camry", vehicles.get().get(0).getModel());
    }

    @Test
    public void getVehiclesByMakeAndModel() {
        String make = "Toyota";
        String model = "Camry";
        Vehicle mockVehicle = mockVehicle();
        Mockito.when(vehicleRepository.findVehiclesByMakeAndModel(make, model)).thenReturn(List.of(mockVehicle));

        Optional<List<Vehicle>> vehicles = vehicleService.getVehiclesByMakeAndModel(make, model);
        assertNotNull(vehicles);
        assertTrue(vehicles.isPresent());
        assertEquals(1, vehicles.get().size());
        assertEquals("Toyota", vehicles.get().get(0).getMake());
        assertEquals("Camry", vehicles.get().get(0).getModel());
    }

    @Test
    public void getVehiclesByType() {
        String type = "Sedan";
        Vehicle mockVehicle = mockVehicle();
        Mockito.when(vehicleRepository.findVehiclesByType(type)).thenReturn(List.of(mockVehicle));

        Optional<List<Vehicle>> vehicles = vehicleService.getVehiclesByType(type);
        assertNotNull(vehicles);
        assertTrue(vehicles.isPresent());
        assertEquals(1, vehicles.get().size());
        assertEquals("Toyota", vehicles.get().get(0).getMake());
        assertEquals("Camry", vehicles.get().get(0).getModel());
    }

    @Test
    public void deleteVehicle() {
        Vehicle vehicle = mockVehicle();
        vehicleService.deleteVehicle(vehicle);
        Mockito.verify(vehicleRepository, Mockito.times(1)).delete(vehicle);
    }

    private Vehicle mockVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Camry");
        vehicle.setType("SUV");
        return vehicle;
    }

    // Add more test methods as needed for other functionalities of VehicleService

}
