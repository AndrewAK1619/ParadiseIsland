package pl.example.components.offer.hotel.room.category;

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
@RequestMapping("/admin/hotels/rooms/categories")
public class RoomCategoryController {

	private RoomCategoryService roomCategoryService;

	@Autowired
	public RoomCategoryController(RoomCategoryService roomCategoryService) {
		this.roomCategoryService = roomCategoryService;
	}

	@GetMapping("")
	public List<RoomCategoryDto> findAll(@RequestParam(required = false) String name) {
		if (name != null)
			return roomCategoryService.findByName(name);
		else
			return roomCategoryService.findAll();
	}

	@PostMapping("")
	public ResponseEntity<?> save(
			@Valid @RequestBody RoomCategoryDto roomCategory, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.ok(ValidationService.valid(result));
		}
		if (roomCategory.getId() != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Saving object can't have setted id");
		RoomCategoryDto savedRoomCategoty = roomCategoryService.save(roomCategory);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedRoomCategoty.getId())
				.toUri();
		return ResponseEntity.created(location).body(savedRoomCategoty);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoomCategoryDto> findById(@PathVariable Long id) {
		return roomCategoryService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@PathVariable Long id, @Valid @RequestBody RoomCategoryDto roomCategory,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.ok(ValidationService.valid(result));
		}
		if (!id.equals(roomCategory.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"The updated object must have an id in accordance with the id "
					+ "in the resource path");
		RoomCategoryDto updatedRoomCategory = roomCategoryService.update(roomCategory);
		return ResponseEntity.ok(updatedRoomCategory);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		roomCategoryService.delete(id);
	}

	@GetMapping("/names")
	public List<String> findAllNames() {
		return roomCategoryService.findAllNames();
	}
}
