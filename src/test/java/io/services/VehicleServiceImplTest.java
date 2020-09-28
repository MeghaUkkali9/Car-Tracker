package io.services;

import io.entity.Vehicle;
import io.exception.VehicleNotFoundException;
import io.repository.VehicleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
public class VehicleServiceImplTest {

    @TestConfiguration
    static class VehicleServiceImplTestConfiguration {

        @Bean
        public VehicleService getService() {
            return new VehicleServiceImpl();
        }
    }

    @Autowired
    private VehicleService service;
    @MockBean
    private VehicleRepository vehicleRepository;

    private List<Vehicle> vehicles;
    private Vehicle vehicle;

    @Before
    public void setup() {
        vehicle = new Vehicle();
        vehicle.setVin("VIN123");
        vehicle.setLastServiceDate(new Date());
        vehicle.setMake("Toyota");
        vehicle.setMaxFuelVolume(12345);
        vehicle.setModel("camry");
        vehicle.setYear(2020);

        vehicles = Collections.singletonList(vehicle);

        Mockito.when(vehicleRepository.findAll())
                .thenReturn(vehicles);

        Mockito.when(vehicleRepository.save(vehicle))
                .thenReturn(vehicle);
    }

    @org.junit.Test
    public void updateVehicle() {
        Vehicle result = service.updateVehicle(vehicle);
        Assert.assertEquals("should match", vehicle, result);
    }

    @org.junit.Test
    public void findAllVehicles() throws VehicleNotFoundException {
        List<Vehicle> result = service.findAllVehicles();
        Assert.assertEquals("should match", vehicles, result);
    }
}