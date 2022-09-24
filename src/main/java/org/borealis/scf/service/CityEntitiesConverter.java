package org.borealis.scf.service;

import org.borealis.scf.model.City;
import org.borealis.scf.model.CityDbo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Kastalski
 */
@Service
public class CityEntitiesConverter implements EntityConverter<City, CityDbo> {

	@Override
	public City convertDbo(final CityDbo dbo) {
		final City dto = new City();
		BeanUtils.copyProperties(dbo, dto);
		return dto;
	}

	@Override
	public CityDbo convertDto(final City dto) {
		final CityDbo dbo = new CityDbo();
		BeanUtils.copyProperties(dto, dbo);
		return dbo;
	}

}
