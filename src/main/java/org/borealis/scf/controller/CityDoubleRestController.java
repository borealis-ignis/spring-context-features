package org.borealis.scf.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.borealis.scf.exception.CityException;
import org.borealis.scf.exception.CityNotFoundException;
import org.borealis.scf.model.City;
import org.borealis.scf.service.CrudService;
import org.borealis.scf.service.RandNumberProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sergey Kastalski
 */
@Tag(name = "city #1.1", description = "version #1.1")
@Slf4j
@RestController
@RequestMapping("/v1.1/city")
public class CityDoubleRestController extends AbstractController {

	private final CrudService<City, CityException> cityService;

	public CityDoubleRestController(final CrudService<City, CityException> cityService, final RandNumberProviderService randNumberProviderService) {
		super(randNumberProviderService);
		this.cityService = cityService;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<City> getCity(@PathVariable final long id) throws CityException {
		printRandID("getCity");
		return ResponseEntity.ok(cityService.read(id));
	}

	@GetMapping(path = {"/", ""})
	public ResponseEntity<List<City>> getCities() throws CityException {
		printRandID("getCities");
		return ResponseEntity.ok(cityService.readAll());
	}

	@PostMapping(path = {"/", ""})
	public ResponseEntity<City> createCity(@RequestBody final City city) throws CityException {
		printRandID("createCity");
		return ResponseEntity.ok(cityService.create(city));
	}

	@PutMapping(path = {"/", ""})
	public ResponseEntity<City> updateCity(@RequestBody final City city) throws CityException {
		printRandID("updateCity");
		return ResponseEntity.ok(cityService.update(city));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean> deleteCity(@PathVariable final long id) throws CityException {
		printRandID("deleteCity");
		return ResponseEntity.ok(cityService.delete(id));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(final Exception e) {
		if (e instanceof CityNotFoundException) {
			log.warn(e.getMessage());
			return ResponseEntity.notFound().build();
		}
		log.error("Smth went wrong", e);
		return ResponseEntity.internalServerError().build();
	}

}
