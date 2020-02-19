package pl.example.components.offer.hotel.room;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/hotels/rooms")
public class RoomController {
	
	RoomService roomService;
	
	@Autowired
	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}
	
    @GetMapping("")
    public List<RoomDto> findAll(@RequestParam(required = false) String roomCategoryName) {
    	System.out.println(roomCategoryName);
        if(roomCategoryName != null) {
            return roomService.findAllByRoomCategory(roomCategoryName);
        }
        else {
        	return roomService.findAll();
        }
    }
    
    @PostMapping("")
    public ResponseEntity<RoomDto> save(@RequestBody RoomDto roomDto) {
        if(roomDto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saving object can't have setted id");
        RoomDto savedRoom = roomService.save(roomDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRoom.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedRoom);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> findById(@PathVariable Long id) {
        return roomService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> update(@PathVariable Long id,
                                           @RequestBody RoomDto roomDto) {
        if(!id.equals(roomDto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The updated object must have an id in accordance with the id in the resource path");
        RoomDto updatedAsset = roomService.update(roomDto);
        return ResponseEntity.ok(updatedAsset);
    }
}
