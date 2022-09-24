package org.borealis.scf.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "city #1.0", description = "version #1.0")
@Slf4j
@RestController
@RequestMapping("/v1.0/city")
public class CityRestController extends AbstractController {

	private final CrudService<City, CityException> cityService;

	public CityRestController(final CrudService<City, CityException> cityService, final RandNumberProviderService randNumberProviderService) {
		super(randNumberProviderService);
		this.cityService = cityService;
	}

	@Operation(
			description = "<b>Full description</b>: <br />Get city by id",
			summary = "Get city by id"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found city by id"),
			@ApiResponse(responseCode = "404", description = "City not found")
	})
	@GetMapping(path = "/{id}")
	public ResponseEntity<City> getCity(@PathVariable final long id) throws CityException {
		printRandID("getCity");
		return ResponseEntity.ok(cityService.read(id));
	}

	@Operation(
			description = "<b>Full description</b>: <br />Get all cities",
			summary = "Get all cities"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found cities")
	})
	@GetMapping(path = "/")
	public ResponseEntity<List<City>> getCities() throws CityException {
		printRandID("getCities");
		return ResponseEntity.ok(cityService.readAll());
	}

	@Operation(
			description = "<b>Full description</b>: <br />Create new city",
			summary = "Create new city"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Created city")
	})
	@PostMapping(path = "/")
	public ResponseEntity<City> createCity(@RequestBody final City city) throws CityException {
		printRandID("createCity");
		return ResponseEntity.ok(cityService.create(city));
	}

	@Operation(
			description = "<b>Full description</b>: <br />Update existing city",
			summary = "Update existing city"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Updated city")
	})
	@PutMapping(path = "/")
	public ResponseEntity<City> updateCity(@RequestBody final City city) throws CityException {
		printRandID("updateCity");
		return ResponseEntity.ok(cityService.update(city));
	}

	@Operation(
			description = "<b>Full description</b>: <br />Delete city by id",
			summary = "Delete city by id"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deleted city, returned 'true'"),
	})
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
