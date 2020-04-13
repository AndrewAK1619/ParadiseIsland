package pl.example.components.offer.hotel.room;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.example.components.offer.hotel.room.image.RoomImageDto;
import pl.example.components.offer.hotel.room.image.RoomImageService;

@RestController
@RequestMapping("/hotels/rooms")
public class RoomController {

	RoomService roomService;
	RoomImageService roomImageService;

	@Autowired
	public RoomController(RoomService roomService, RoomImageService roomImageService) {
		this.roomService = roomService;
		this.roomImageService = roomImageService;
	}
	
	@GetMapping("")
	public ResponseEntity<MultiValueMap<String, Object>> findAll(@RequestParam(required = false) String roomCategoryName) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		List<RoomDto> roomDtoList;
		
		if (roomCategoryName != null) {
			roomDtoList = roomService.findAllByRoomCategory(roomCategoryName);
		} else {
			roomDtoList = roomService.findAll();
		}
		List<byte[]> mainImgList = roomDtoList.stream()
					.map(roomDto -> {
						try {
							return roomService.getMainImageInByteFromRoom(roomDto.getId());
						} catch (IOException e) {
							e.printStackTrace();
						}
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
								"Downloading object failed");
					})
					.collect(Collectors.toList());
		
		formData.add("roomList", roomDtoList);
		formData.add("fileList", mainImgList);
		
		return ResponseEntity.ok(formData);
	}
	
	@PostMapping("")
	public ResponseEntity<RoomDto> save(@RequestPart(name = "file", required = false) MultipartFile file, 
			@RequestPart("roomDto") String roomDtoJson) throws JsonMappingException, JsonProcessingException {
		
		RoomDto roomDto = new ObjectMapper().readValue(roomDtoJson, RoomDto.class);
		if (roomDto.getId() != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Saving object can't have setted id");
		
		RoomImageDto roomImageDtoSave = roomImageService.saveRoomImage(file);
		if (roomImageDtoSave != null) 
			roomDto.setMainImageId(roomImageDtoSave.getId());
		
		RoomDto savedRoom = roomService.save(roomDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedRoom.getId()).toUri();
		return ResponseEntity.created(location).body(savedRoom);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MultiValueMap<String, Object>> findById(@PathVariable Long id) throws IOException {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        RoomDto roomDto = null;
		Optional<RoomDto> roomDtoOptional = roomService.findById(id);
		
        if(roomDtoOptional.isPresent())
        	roomDto = roomDtoOptional.get();
        else
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
        			"Downloading object failed");
	
		byte[] bytes = roomService.getMainImageInByteFromRoom(roomDto.getId());
		formData.add("room", roomDto);
		formData.add("file", bytes);
		return ResponseEntity.ok(formData);
	}

	@PutMapping("/{id}")
    public ResponseEntity<RoomDto> update(@PathVariable Long id,
    		@RequestPart(name = "idRoom", required = false) String idRoom, 
    		@RequestPart(name = "file", required = false) MultipartFile file, 
    		@RequestPart("roomDto") String roomDtoJson) throws IOException {
    	
		RoomDto roomDto = new ObjectMapper().readValue(roomDtoJson, RoomDto.class);
        if(!id.equals(roomDto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
            		"The updated object must have an id in accordance with the id in the resource path");
        
		RoomImageDto roomImageDtoSave = roomImageService.saveRoomImage(file);
		if (roomImageDtoSave != null)
			roomDto.setMainImageId(roomImageDtoSave.getId());
        
        RoomDto updatedRoom = roomService.update(roomDto);
        return ResponseEntity.ok(updatedRoom);
    }
	
	@GetMapping("/defaultImg")
	public ResponseEntity<?> getDefaultImage() throws IOException {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("file", roomImageService.getDefaultMainImageInByte());
		return ResponseEntity.ok(formData);
	}
}