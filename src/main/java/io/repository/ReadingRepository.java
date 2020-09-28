package io.repository;

import io.entity.Reading;
import org.springframework.data.repository.CrudRepository;

public interface ReadingRepository extends CrudRepository<Reading,String> {

}
