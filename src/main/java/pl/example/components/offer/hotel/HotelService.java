package pl.example.components.offer.hotel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.ParadiseIslandApplication;
import pl.example.components.offer.booking.OfferBooking;
import pl.example.components.offer.hotel.image.HotelImage;
import pl.example.components.offer.hotel.image.HotelImageService;
import pl.example.components.offer.location.city.City;
import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.region.Region;

@Service
public class HotelService {

	private HotelRepository hotelRepository;
	private HotelMapper hotelMapper;

	@Autowired
	public HotelService(
			HotelRepository hotelRepository, 
			HotelMapper hotelMapper) {
		this.hotelRepository = hotelRepository;
		this.hotelMapper = hotelMapper;
	}

	public Optional<HotelDto> findById(long hotelId) {
		return hotelRepository.findById(hotelId)
				.map(hotelMapper::toDto);
	}

	public Page<HotelDto> findByIdWithPage(long hotelId, Pageable pageable) {
		return hotelRepository.findById(hotelId, pageable)
				.map(hotelMapper::toDto);
	}

	public Page<HotelDto> findAllWithPage(int pageNumber) {
		Page<Hotel> page = hotelRepository.findAllHotel(PageRequest.of(pageNumber, 10));
		return page.map(hotelMapper::toDto);
	}

	public List<String> findAllNames() {
		return hotelRepository.findAll()
				.stream()
				.map(Hotel::getHotelName)
				.collect(Collectors.toList());
    }

	Page<HotelDto> findAllByHotelNameAndCountryNameWithPage(
			String hotelName, String countryName, int pageNumber) {
		Page<Hotel> page = hotelRepository.findAllByHotelNameAndCountryName(
				hotelName, countryName, PageRequest.of(pageNumber, 10));
		return page.map(hotelMapper::toDto);
	}

	public Page<HotelDto> findAllByCountryWithPage(Country country, Integer pageNumber) {
		return hotelRepository.findAllByCountry(country, PageRequest.of(pageNumber, 10))
				.map(hotelMapper::toDto);
	}

	public Page<HotelDto> findAllByRegionWithPage(Region region, Integer pageNumber) {
		return hotelRepository.findAllByRegion(region, PageRequest.of(pageNumber, 10))
				.map(hotelMapper::toDto);
	}

	public Page<HotelDto> findAllByCityWithPage(City city, Integer pageNumber) {
		return hotelRepository.findAllByCity(city, PageRequest.of(pageNumber, 10))
				.map(hotelMapper::toDto);
	}

	public List<HotelSearchDto> findAllHotelSearch() {
		return hotelRepository.findAll()
				.stream()
				.map(HotelSearchMapper::toDto)
				.collect(Collectors.toList());
	}

	public List<byte[]> getMainImgListInByteByHotelDtoList(Page<HotelDto> hotelDtoList) {
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

	public byte[] getMainImageInByteFromHotel(Long hotelId) throws IOException {
		String mainImagePathByHotelId = findMainImagePathFromHotel(hotelId);
		String partOfPathToCheckLocation = mainImagePathByHotelId.substring(1, 7);
		File file;		
		if("static".equals(partOfPathToCheckLocation)) {
			ClassPathResource classPathResource = new ClassPathResource(mainImagePathByHotelId);
			InputStream inputStream = classPathResource.getInputStream();
			file = File.createTempFile("test", ".jpg");
			FileUtils.copyInputStreamToFile(inputStream, file);
		} else {
			ApplicationHome home = new ApplicationHome(ParadiseIslandApplication.class);
			String homeDir = home.getDir().getPath();
			String fullPathToSlashReplace = homeDir + mainImagePathByHotelId;
			String fullPath = fullPathToSlashReplace.replace("\\", "/");
			file = new File(fullPath);
		}
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

	public HotelDto getHotelDtoByOfferBooking(OfferBooking offerBooking) {
		Hotel hotel = offerBooking.getHotelBooking().getHotel();
		return hotelMapper.toDto(hotel);
	}

	HotelDto save(HotelDto hotelDto) {
		return mapAndSaveHotel(hotelDto);
	}

	HotelDto update(HotelDto hotelDto) {
		return mapAndSaveHotel(hotelDto);
	}

	public void delete(Long id) {
		hotelRepository.deleteById(id);
	}

	private HotelDto mapAndSaveHotel(HotelDto hotelDto) {
		Hotel hotelEntity = hotelMapper.toEntity(hotelDto);
		if(hotelEntity.getId() != null) {
			Optional<Hotel> hotelOptional = hotelRepository.findById(hotelEntity.getId());
			hotelOptional.ifPresent(h -> 
				hotelEntity.setHotelAdvantages(h.getHotelAdvantages()));
		}
		Hotel savedHotel = hotelRepository.save(hotelEntity);
		return hotelMapper.toDto(savedHotel);
	}
}