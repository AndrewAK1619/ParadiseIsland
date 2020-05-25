package pl.example.components.offer.hotel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.hotel.image.HotelImage;
import pl.example.components.offer.hotel.image.HotelImageService;

@Service
public class HotelService {

	private HotelRepository hotelRepository;
	private HotelMapper hotelMapper;

	@Autowired
	public HotelService(HotelRepository hotelRepository, 
			HotelMapper hotelMapper) {
		this.hotelRepository = hotelRepository;
		this.hotelMapper = hotelMapper;
	}

	Optional<HotelDto> findById(long id) {
		return hotelRepository.findById(id)
				.map(hotelMapper::toDto);
	}

	List<HotelDto> findAll() {
		return hotelRepository.own()
				.stream()
				.map(hotelMapper::toDto)
				.collect(Collectors.toList());
	}

	List<HotelDto> findAllByHotelName(String hotelName) {
		return hotelRepository.findAllByHotelNameContainingIgnoreCase(hotelName)
				.stream()
				.map(hotelMapper::toDto)
				.collect(Collectors.toList());
	}
	
	List<byte[]> getMainImgListInByteByHotelDtoList(List<HotelDto> hotelDtoList) {
		return hotelDtoList.stream()
			.map(hotelDto -> {
				try {
					return getMainImageInByteFromHotel(hotelDto.getId());
				} catch (IOException e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
							"Downloading object failed");
				}
			}).collect(Collectors.toList());
	}

	byte[] getMainImageInByteFromHotel(Long hotelId) throws IOException {
		File file = new File(findMainImagePathFromHotel(hotelId));
		byte[] bytes = Files.readAllBytes(file.toPath());
		return bytes;
	}

	private String findMainImagePathFromHotel(Long hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		List<HotelImage> hotelImages = getHotelImagesByOptionalHotel(hotel);
		String imagePath = "";

		if (hotelImages.size() > 1) {
			throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, 
					"Too many main image downloaded");
		} else if (hotelImages.size() == 1) {
			List<HotelImage> mainImage = hotelImages.stream()
					.filter(findMainImage -> findMainImage.isMainImage() == true)
					.collect(Collectors.toList());
			imagePath = mainImage.get(0).getImagePath();
		} else {
			imagePath = HotelImageService.DEFAULT_IMAGE_PATH;
		}
		return imagePath;
	}
	
	private List<HotelImage> getHotelImagesByOptionalHotel(Optional<Hotel> hotel) {
		if (hotel.isPresent()) {
			List<HotelImage> hotelImages = hotel.get().getHotelImages();
			hotelImages = hotelImages.stream()
					.filter(hotelImg -> hotelImg.isMainImage() == true)
					.collect(Collectors.toList());
			return hotelImages;
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Downloading object failed");
	}

	HotelDto save(HotelDto hotelDto) {
		return mapAndSaveHotel(hotelDto);
	}

	HotelDto update(HotelDto hotelDto) {
		return mapAndSaveHotel(hotelDto);
	}

	private HotelDto mapAndSaveHotel(HotelDto hotelDto) {
		Hotel hotelEntity = hotelMapper.toEntity(hotelDto);
		Hotel savedHotel = hotelRepository.save(hotelEntity);
		return hotelMapper.toDto(savedHotel);
	}
}
