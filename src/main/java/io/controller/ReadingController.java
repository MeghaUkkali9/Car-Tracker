package io.controller;

import io.entity.Reading;
import io.services.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReadingController {
    @Autowired
    private ReadingService readingService;

    @RequestMapping(method = RequestMethod.POST, value = "/readings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Reading createVehicleReading(@RequestBody Reading reading) throws Exception {
        return readingService.createVehicleReading(reading);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getgeolocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reading> getVehiclesGeoLocation() {
        return readingService.getVehiclesGeoLocation();
    }
}
