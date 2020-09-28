package io.repository;

import io.entity.Alert;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertRepository extends CrudRepository<Alert,String> {
       List<Alert> findAllByVin(String vin);

       List<Alert> findAllByPriority(String highAlert);
}
