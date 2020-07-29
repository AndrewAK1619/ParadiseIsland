package pl.example.components.offer.transport.airline;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.example.components.validation.ValidationService;

@RestController
@RequestMapping("/admin/airlines")
public class AirlineController {

	private AirlineService airlineService;

	@Autowired
	public AirlineController(AirlineService airlineService) {
		this.airlineService = airlineService;
	}

	@GetMapping("")
	public List<AirlineDto> findAll(@RequestParam(required = false) String airlineName) {
		if (airlineName != null)
			return airlineService.findByAirlineName(airlineName);
		else
			return airlineService.findAll();
	}

	@PostMapping("")
	public ResponseEntity<?> save(@Valid @RequestBody AirlineDto airline, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.ok(ValidationService.valid(result));
		}
		if (airline.getId() != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Saving object can't have setted id");
		AirlineDto savedAirline = airlineService.save(airline);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedAirline.getId()).toUri();
		return ResponseEntity.created(location).body(savedAirline);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AirlineDto> findById(@PathVariable Long id) {
		return airlineService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AirlineDto airline,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.ok(ValidationService.valid(result));
		}
		if (!id.equals(airline.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"The updated object must have an id in accordance with the id in the resource path");
		AirlineDto updatedAirline = airlineService.update(airline);
		return ResponseEntity.ok(updatedAirline);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		airlineService.delete(id);
	}
}
