package pl.example.components.offer.hotel.advantages;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/admin/hotels")
public class HotelAdvantageController {

	HotelAdvantageService hotelAdvantageService;

	@Autowired
	public HotelAdvantageController(HotelAdvantageService hotelAdvantageService) {
		this.hotelAdvantageService = hotelAdvantageService;
	}

	@GetMapping("/{hotelId}/advantages")
	public List<HotelAdvantageDto> findAllByHotelId(
			@PathVariable Long hotelId) {
		return hotelAdvantageService.findAllByHotelId(hotelId);
	}
	
	@GetMapping("/{hotelId}/advantages/{id}")
	public ResponseEntity<HotelAdvantageDto> findById(
			@PathVariable Long id) {
		return hotelAdvantageService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/advantages")
	public ResponseEntity<?> save(@RequestBody HotelAdvantageDto hotelAdvantageDto) {
		if (hotelAdvantageDto.getId() != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Saving object can't have setted id");
		HotelAdvantageDto savedHotelAdvantage = hotelAdvantageService.save(hotelAdvantageDto);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedHotelAdvantage.getId())
				.toUri();
		return ResponseEntity.created(location).body(savedHotelAdvantage);
	}

	@PutMapping("/advantages/{id}")
	public ResponseEntity<?> update(
			@PathVariable Long id, 
			@RequestBody HotelAdvantageDto hotelAdvantageDto) {
		if (!id.equals(hotelAdvantageDto.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"The updated object must have an id in accordance with the id "
					+ "in the resource path");
		HotelAdvantageDto updatedHotelAdvantage = hotelAdvantageService.update(hotelAdvantageDto);
		return ResponseEntity.ok(updatedHotelAdvantage);
	}

    @DeleteMapping("/advantages/{id}")
    public void delete(@PathVariable Long id) {
    	hotelAdvantageService.delete(id);
    }
}
