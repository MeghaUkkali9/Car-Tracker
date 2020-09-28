package io.services;

import io.entity.Alert;
import io.exception.AlertsNotFoundException;
import io.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAlerts() throws AlertsNotFoundException {
        return (List<Alert>) alertRepository.findAll();
    }

    @Override
    @Transactional
    public void insertAlert(Alert alert) {
        alertRepository.save(alert);
    }

    @Transactional(readOnly = true)
    public List<Alert> getVehicleAlerts(String vin) throws AlertsNotFoundException {
        return alertRepository.findAllByVin(vin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> fetchHighAlert() {
        List<Alert> alertList = alertRepository.findAllByPriority("HIGH");
        List<Alert> alertListResult = new ArrayList<>();
        Date todayDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);
        cal.add(Calendar.HOUR, -2);
        Date twoHourBack = cal.getTime();
        System.out.println("todayDate==" + todayDate + "thirtyMinutesBack==" + twoHourBack);
        for (Alert alert : alertList) {
            System.out.println("reading.getTimestamp==" + alert.getTimeStamp().getTime());
            if (alert.getTimeStamp().getTime() < todayDate.getTime() &&
                    alert.getTimeStamp().getTime() > twoHourBack.getTime()) {
                alertListResult.add(alert);
            }
        }
        return alertListResult;
    }
}
