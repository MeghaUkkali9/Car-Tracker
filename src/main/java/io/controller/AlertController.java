package io.controller;

import io.entity.Alert;
import io.exception.AlertsNotFoundException;
import io.services.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertController {
    @Autowired
    private AlertService alertService;

    @RequestMapping(method = RequestMethod.GET, value = "/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Alert> getAlerts() throws AlertsNotFoundException {
        return alertService.getAlerts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getHighAlerts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Alert> fetchHighAlert() {
        return alertService.fetchHighAlert();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getVehicleAlert/{vin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Alert> getVehicleAlerts(@PathVariable String vin) throws AlertsNotFoundException {
        return alertService.getVehicleAlerts(vin);
    }

}
