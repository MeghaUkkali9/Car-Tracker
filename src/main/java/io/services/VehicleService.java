package io.services;

import io.entity.Vehicle;
import io.exception.VehicleNotFoundException;

import java.util.List;

public interface VehicleService {
    Vehicle updateVehicle(Vehicle vehicle);


    List<Vehicle> findAllVehicles() throws VehicleNotFoundException;
}
