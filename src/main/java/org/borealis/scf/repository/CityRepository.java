package org.borealis.scf.repository;

import org.borealis.scf.model.CityDbo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Kastalski
 */
@Repository
public interface CityRepository extends CrudRepository<CityDbo, Long> {
}
