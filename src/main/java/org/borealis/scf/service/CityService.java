package org.borealis.scf.service;

import lombok.extern.slf4j.Slf4j;
import org.borealis.scf.component.annotation.PostContextInitializator;
import org.borealis.scf.component.annotation.Profiling;
import org.borealis.scf.component.annotation.RandomInteger;
import org.borealis.scf.component.PostContextInitializer;
import org.borealis.scf.exception.CityException;
import org.borealis.scf.exception.CityNotFoundException;
import org.borealis.scf.model.City;
import org.borealis.scf.model.CityDbo;
import org.borealis.scf.repository.CityRepository;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergey Kastalski
 */
@Slf4j
@Service
public class CityService implements CrudService<City, CityException>, PostContextInitializer {

	@RandomInteger(min=2, max=5)
	private int serviceSerialNumber;

	private final CityRepository cityRepository;

	private final EntityConverter<City, CityDbo> converter;

	public CityService(final CityRepository cityRepository, final EntityConverter<City, CityDbo> converter) {
		this.cityRepository = cityRepository;
		this.converter = converter;
		log.info("Phase #1: " + serviceSerialNumber);
	}

	@PostConstruct
	public void init() {
		log.info("Phase #2: " + serviceSerialNumber);
	}

	@PreDestroy
	public void destroy() {
		log.info("Bye-bye from " + getClass().getSimpleName() + ": " + serviceSerialNumber);
	}

	@Override
	public void initAfterContext() {
		log.info("Phase #3 [interface] (after increment): " + ++serviceSerialNumber);
	}

	@PostContextInitializator
	public void initAfterContext2() {
		log.info("Phase #3 [annotation] (after increment): " + ++serviceSerialNumber);
	}

	@Override
	public City read(final long id) throws CityException {
		printRandNumber("read");
		final CityDbo dbo = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException("Can't find city by id = " + id));
		return converter.convertDbo(dbo);
	}

	@Profiling
	@Override
	public List<City> readAll() {
		printRandNumber("readAll");
		final List<City> cities = new ArrayList<>();
		cityRepository.findAll().forEach(dbo -> cities.add(converter.convertDbo(dbo)));
		return cities;
	}

	@Override
	public City create(final City city) {
		printRandNumber("create");
		return converter.convertDbo(cityRepository.save(converter.convertDto(city)));
	}

	@Override
	public City update(final City city) throws CityNotFoundException {
		printRandNumber("update");
		cityRepository.findById(city.getId()).orElseThrow(() -> new CityNotFoundException(city + " doesn't exists"));
		return converter.convertDbo(cityRepository.save(converter.convertDto(city)));
	}

	@Override
	public boolean delete(final long id) {
		printRandNumber("delete");
		final Optional<CityDbo> optionalCity = cityRepository.findById(id);
		if (optionalCity.isEmpty()) {
			return true;
		}
		cityRepository.deleteById(id);
		return cityRepository.findById(id).isEmpty();
	}

	private void printRandNumber(final String methodName) {
		log.info("Method: " + methodName + "(), rand operation id=" + randNumberProviderService().getRandNumber());
	}

	@Lookup
	public RandNumberProviderService randNumberProviderService() {
		return null;
	}

}
