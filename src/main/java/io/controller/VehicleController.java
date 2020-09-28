package io.controller;

import io.entity.Vehicle;
import io.exception.VehicleNotFoundException;
import io.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.GET, value = "/getVehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> findAllVehicles() throws VehicleNotFoundException {
        return vehicleService.findAllVehicles();
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> updateVehicle(@RequestBody List<Vehicle> vehicle) {
        for (Vehicle individualVehicle : vehicle) {
            vehicleService.updateVehicle(individualVehicle);
        }
        return vehicle;
    }


}
