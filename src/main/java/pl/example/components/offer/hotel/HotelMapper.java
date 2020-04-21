package pl.example.components.offer.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.image.HotelImage;
import pl.example.components.offer.hotel.image.HotelImageRepository;

@Service
public class HotelMapper {

	private HotelRepository hotelRepository;
	private HotelImageRepository hotelImageRepository;

	public HotelMapper(HotelRepository hotelRepository, 
			HotelImageRepository hotelImageRepository) {
		this.hotelRepository = hotelRepository;
		this.hotelImageRepository = hotelImageRepository;
	}

	HotelDto toDto(Hotel hotel) {
		HotelDto dto = new HotelDto();
		dto.setId(hotel.getId());
		dto.setHotelName(hotel.getHotelName());
		dto.setDescription(hotel.getDescription());
		return dto;
	}

	Hotel toEntity(HotelDto hotelDto) {
		Hotel entity = new Hotel();
		entity.setId(hotelDto.getId());
		entity.setHotelName(hotelDto.getHotelName());
		entity.setDescription(hotelDto.getDescription());

		List<HotelImage> hotelImages = addCorrectHotelImageList(hotelDto);

		entity.setHotelImages(hotelImages);
		return entity;
	}

	private List<HotelImage> addCorrectHotelImageList(HotelDto hotelDto) {
		List<HotelImage> hotelImages = new ArrayList<>();
		if (hotelDto.getId() != null && hotelDto.getMainImageId() != null) {
			hotelImages = getHotelImageFromHotel(hotelDto, hotelImages);
			hotelImages.stream().forEach(hotelImg -> hotelImg.setMainImage(false));
		} else if (hotelDto.getId() != null && hotelDto.getMainImageId() == null) {
			hotelImages = getHotelImageFromHotel(hotelDto, hotelImages);
		}
		if (hotelDto.getMainImageId() != null) {
			Optional<HotelImage> mainHotelImage = hotelImageRepository
					.findById(hotelDto.getMainImageId());
			if (mainHotelImage.isPresent())
				hotelImages.add(mainHotelImage.get());
		}
		return hotelImages;
	}

	private List<HotelImage> getHotelImageFromHotel(HotelDto hotelDto, 
			List<HotelImage> hotelImages) {
		Optional<Hotel> hotelBeforeUpdated = hotelRepository.findById(hotelDto.getId());
		if (hotelBeforeUpdated.isPresent()) {
			hotelImages = hotelBeforeUpdated.get().getHotelImages();
		}
		return hotelImages;
	}
}
