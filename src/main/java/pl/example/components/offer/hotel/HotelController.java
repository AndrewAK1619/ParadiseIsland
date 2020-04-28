package pl.example.components.offer.hotel;

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

import pl.example.components.offer.hotel.image.HotelImageDto;
import pl.example.components.offer.hotel.image.HotelImageService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	private HotelService hotelService;
	private HotelImageService hotelImageService;

	@Autowired
	public HotelController(HotelService hotelService, 
			HotelImageService hotelImageService) {
		this.hotelService = hotelService;
		this.hotelImageService = hotelImageService;
	}

	@GetMapping("")
	public ResponseEntity<MultiValueMap<String, Object>> findAll(
			@RequestParam(required = false) String hotelName) {

		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		List<HotelDto> hotelDtoList;

		if (hotelName != null) {
			hotelDtoList = hotelService.findAllByHotelName(hotelName);
		} else {
			hotelDtoList = hotelService.findAll();
		}
		List<byte[]> mainImgList = hotelDtoList.stream()
					.map(hotelDto -> {
						try {
							return hotelService.getMainImageInByteFromHotel(hotelDto.getId());
						} catch (IOException e) {
							throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
									"Downloading object failed");
						}
					})
					.collect(Collectors.toList());

		formData.add("hotelList", hotelDtoList);
		formData.add("fileList", mainImgList);
		
		return ResponseEntity.ok(formData);
	}

	@PostMapping("")
	public ResponseEntity<HotelDto> save(
			@RequestPart(name = "file", required = false) MultipartFile file,
			@RequestPart("hotelDto") String hotelDtoJson) 
					throws JsonMappingException, JsonProcessingException {
		
		HotelDto hotelDto = new ObjectMapper().readValue(hotelDtoJson, HotelDto.class);
		if (hotelDto.getId() != null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Saving object can't have setted id");

		HotelImageDto hotelImageDtoSave = hotelImageService.saveHotelImage(file);

		if (hotelImageDtoSave != null)
			hotelDto.setMainImageId(hotelImageDtoSave.getId());

		HotelDto savedHotel = hotelService.save(hotelDto);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedHotel.getId())
				.toUri();
		return ResponseEntity.created(location).body(savedHotel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MultiValueMap<String, Object>> findById(@PathVariable Long id) 
			throws IOException {
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		HotelDto hotelDto = null;
		Optional<HotelDto> hotelDtoOptional = hotelService.findById(id);

		if (hotelDtoOptional.isPresent())
			hotelDto = hotelDtoOptional.get();
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Downloading object failed");

		byte[] bytes = hotelService.getMainImageInByteFromHotel(hotelDto.getId());
		formData.add("hotel", hotelDto);
		formData.add("file", bytes);
		return ResponseEntity.ok(formData);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HotelDto> update(@PathVariable Long id,
			@RequestPart(name = "idHotel", required = false) String idHotel,
			@RequestPart(name = "file", required = false) MultipartFile file,
			@RequestPart("hotelDto") String hotelDtoJson) throws IOException {

		HotelDto hotelDto = new ObjectMapper().readValue(hotelDtoJson, HotelDto.class);
		if (!id.equals(hotelDto.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"The updated object must have an id in accordance with the id in the resource path");

		HotelImageDto hotelImageDtoSave = hotelImageService.saveHotelImage(file);
		if (hotelImageDtoSave != null)
			hotelDto.setMainImageId(hotelImageDtoSave.getId());

		HotelDto updatedHotel = hotelService.update(hotelDto);
		return ResponseEntity.ok(updatedHotel);
	}

	@GetMapping("/defaultImg")
	public ResponseEntity<?> getDefaultImage() throws IOException {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("file", hotelImageService.getDefaultMainImageInByte());
		return ResponseEntity.ok(formData);
	}
}
