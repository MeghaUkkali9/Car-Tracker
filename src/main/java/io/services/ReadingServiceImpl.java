package io.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.entity.Alert;
import io.entity.Reading;
import io.entity.Tire;
import io.entity.Vehicle;
import io.repository.ReadingRepository;
import io.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReadingServiceImpl implements ReadingService {
    @Autowired
    private ReadingRepository readingRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private AlertService alertService;

    @Autowired
    private ObjectMapper mapper;

    private void checkAlert(Vehicle vehicle, Reading reading) throws Exception {
        if (vehicle.getRedlineRpm() < reading.getEngineRpm()) {
            Date today = new Date();
            Alert alert = new Alert();
            alert.setPriority("HIGH");
            alert.setReason("higher engine RPM");
            alert.setTimeStamp(today);
            alert.setVin(vehicle.getVin());
//            String message = mapper.writeValueAsString(alert);
//            notification.publish("High Alert", message);
            alertService.insertAlert(alert);
        }
        if (0.1 * vehicle.getMaxFuelVolume() > reading.getFuelVolume()) {
            Date today = new Date();
            Alert alert = new Alert();
            alert.setPriority("MEDIUM");
            alert.setReason("Max Fuel Volume");
            alert.setTimeStamp(today);
            alert.setVin(vehicle.getVin());
            alertService.insertAlert(alert);
//            String message = mapper.writeValueAsString(alert);
//            notification.publish("MEDIUM fuel Alert", message);
        }
        if (reading.isEngineCoolantLow() || reading.isCheckEngineLightOn()) {
            Date today = new Date();
            Alert alert = new Alert();
            alert.setPriority("LOW");
            if (reading.isEngineCoolantLow()) {
                alert.setReason("Low Engine Coolant");
            } else {
                alert.setReason("Engine Light On");
            }
            alert.setTimeStamp(today);
            alert.setVin(vehicle.getVin());
//            String message = mapper.writeValueAsString(alert);
//            notification.publish("LOW engine Alert", message);
            alertService.insertAlert(alert);
        }
        Tire tires = reading.getTires();
        int[] tirePressures = {tires.getFrontLeft(), tires.getFrontRight(), tires.getRearLeft(), tires.getRearRight()};
        for (int pressure : tirePressures) {
            if (pressure < 32 || pressure > 36) {
                Date today = new Date();
                Alert alert = new Alert();
                alert.setPriority("LOW");
                if (pressure < 32)
                    alert.setReason("Low Pressure");
                else
                    alert.setReason("High Tire Pressure");
                alert.setTimeStamp(today);
                alert.setVin(vehicle.getVin());
//                String message = mapper.writeValueAsString(alert);
//                notification.publish("LOW pressure Alert", message);
                alertService.insertAlert(alert);
                break;
            }
        }
    }

    public Reading createVehicleReading(Reading reading) throws Exception {
        String vin = reading.getVin();
        Optional<Vehicle> vehicle = vehicleRepository.findByVin(vin);
        if (!vehicle.isPresent()) {
            return null;
        } else {
            checkAlert(vehicle.get(), reading);
        }
        return readingRepository.save(reading);
    }

    @Override
    public List<Reading> getVehiclesGeoLocation() {
        List<Reading> readingList = (List<Reading>) readingRepository.findAll();
        List<Reading> readingListResult = new ArrayList<>();
        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();
// remove next line if you're always using the current time.
        cal.setTime(todayDate);
        cal.add(Calendar.MINUTE, -30);
        Date thirtyMinutesBack = cal.getTime();
        System.out.println("todayDate==" + todayDate + "thirtyMinutesBack==" + thirtyMinutesBack);
        for (Reading reading : readingList) {
            System.out.println("reading.getTimestamp==" + reading.getTimestamp().getTime());
            if (reading.getTimestamp().getTime() < todayDate.getTime() &&
                    reading.getTimestamp().getTime() > thirtyMinutesBack.getTime()) {
                readingListResult.add(reading);
            }
        }
        return readingListResult;
    }
}
