package io.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.entity.Reading;

import java.util.List;

public interface ReadingService {
    public Reading createVehicleReading(Reading reading) throws Exception;

    public List<Reading> getVehiclesGeoLocation();
}
