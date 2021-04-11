package org.rebelalliance.daos;

import java.util.List;
import org.rebelalliance.entities.Satellite;
import org.springframework.data.repository.CrudRepository;

public interface SatelliteDAO extends CrudRepository<Satellite, Long> {
	List<Satellite> findByName(String name);
}
