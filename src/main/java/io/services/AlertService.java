package io.services;

import io.entity.Alert;
import io.exception.AlertsNotFoundException;

import java.util.List;


public interface AlertService {
    List<Alert> getAlerts() throws AlertsNotFoundException;

    void insertAlert(Alert alert);

    List<Alert> getVehicleAlerts(String vin) throws AlertsNotFoundException;

    List<Alert> fetchHighAlert();
}
