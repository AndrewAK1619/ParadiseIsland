package pl.example.components.offer.hotel.room;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.ParadiseIslandApplication;
import pl.example.components.offer.booking.OfferBooking;
import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.room.category.RoomCategory;
import pl.example.components.offer.hotel.room.category.RoomCategoryRepository;
import pl.example.components.offer.hotel.room.image.RoomImage;
import pl.example.components.offer.hotel.room.image.RoomImageRepository;
import pl.example.components.offer.hotel.room.image.RoomImageService;

@Service
public class RoomService {

	RoomRepository roomRepository;
	RoomCategoryRepository roomCategoryRepository;
	RoomImageRepository roomImageRepository;
	RoomMapper roomMapper;
	RoomImageService roomImageService;

	@Autowired
	public RoomService(RoomRepository roomRepository, 
			RoomCategoryRepository roomCategoryRepository,
			RoomImageRepository roomImageRepository, 
			RoomMapper roomMapper, 
			RoomImageService roomImageService) {
		this.roomRepository = roomRepository;
		this.roomCategoryRepository = roomCategoryRepository;
		this.roomImageRepository = roomImageRepository;
		this.roomMapper = roomMapper;
		this.roomImageService = roomImageService;
	}

	List<RoomDto> findAll() {
		return roomRepository.findAll()
				.stream()
				.map(roomMapper::toDto)
				.collect(Collectors.toList());
	}

	List<RoomDto> findAllByHotelId(long hotelId) {
		return roomRepository.findAllByHotelId(hotelId)
				.stream()
				.map(roomMapper::toDto)
				.collect(Collectors.toList());
	}

	List<RoomDto> findAllByHotelIdAndRoomCategory(long hotelId, String roomCategoryName) {
		List<RoomCategory> roomCategoryList = roomCategoryRepository
				.findAllByNameContainingIgnoreCaseOrderByIdAsc(roomCategoryName);

		List<RoomDto> listRoomDto = new ArrayList<>();

		for (RoomCategory roomCategory : roomCategoryList) {
			listRoomDto.addAll(roomRepository
					.findAllByHotelIdAndRoomCategoryOrderByHotelId(hotelId, roomCategory)
					.stream()
					.map(roomMapper::toDto)
					.collect(Collectors.toList()));
		}
		listRoomDto.sort((a, b) -> a.getId().compareTo(b.getId()));
		return listRoomDto;
	}

	List<RoomDto> findAllByNumberOfSingleBeds(int numberOfBeds) {
		return roomRepository.findAllByNumberOfSingleBeds(numberOfBeds)
				.stream()
				.map(roomMapper::toDto)
				.collect(Collectors.toList());
	}

	List<RoomDto> findAllByNumberOfDoubleBeds(int numberOfBeds) {
		return roomRepository.findAllByNumberOfDoubleBeds(numberOfBeds)
				.stream()
				.map(roomMapper::toDto)
				.collect(Collectors.toList());
	}

	Optional<RoomDto> findById(long id) {
		return roomRepository.findById(id).map(roomMapper::toDto);
	}
	
	public List<RoomDto> findAvailableRooms(HotelDto hotelDto, LocalDate departure,
			LocalDate returnDate, Integer persons) {
		return roomRepository.findAvailableRooms(
				hotelDto.getId(), 
				departure.plusDays(1), 
				returnDate.plusDays(1), 
				persons)
			.stream()
			.map(r -> roomMapper.toDto(r))
			.collect(Collectors.toList());
	}
	
	public List<RoomDto> findAvailableRooms(HotelDto hotelDto, LocalDate departure,
			LocalDate returnDate, Integer persons, RoomCategory roomCategory) {
		return roomRepository.findAvailableRoomsByCategory(
				hotelDto.getId(), 
				departure.plusDays(1), 
				returnDate.plusDays(1), 
				persons,
				roomCategory.getId())
			.stream()
			.map(r -> roomMapper.toDto(r))
			.collect(Collectors.toList());
	}
	
	public List<RoomDto> getAllRoomWhereIsMinimalCostBySearchData(
			Page<HotelDto> hotelDtoList, LocalDate departure,
			LocalDate returnDate, Integer persons) {
			
		List<List<Room>> rooms = new ArrayList<>();
		rooms = hotelDtoList
				.stream()
				.map(h -> roomRepository.findAvailableRooms(
								h.getId(), 
								departure.plusDays(1), 
								returnDate.plusDays(1), 
								persons))
				.collect(Collectors.toList());

		return rooms.stream()
				.map(rs -> {
					if(rs.isEmpty()) {
						return null;
					} else {
						return rs.get(0);
					}
				})
				.map(r -> {
					if(r == null) {
						return null;
					} else {
						return roomMapper.toDto(r);
					}
				})
				.collect(Collectors.toList());
	}

	public List<byte[]> getMainImgListInByteByRoomDtoList(List<RoomDto> roomDtoList) {
		return roomDtoList.stream()
			.map(roomDto -> {
				try {
					return getMainImageInByteFromRoom(roomDto.getId());
				} catch (IOException e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
							"Downloading object failed");
				}
			}).collect(Collectors.toList());
	}

	byte[] getMainImageInByteFromRoom(Long roomId) throws IOException {
		String mainImagePathByRoomId = findMainImagePathFromRoom(roomId);
		String partOfPathToCheckLocation = mainImagePathByRoomId.substring(1, 7);
		File file;	
		if("static".equals(partOfPathToCheckLocation)) {
			ClassPathResource classPathResource = new ClassPathResource(mainImagePathByRoomId);
			InputStream inputStream = classPathResource.getInputStream();
			file = File.createTempFile("test", ".jpg");
			FileUtils.copyInputStreamToFile(inputStream, file);
		} else {
			ApplicationHome home = new ApplicationHome(ParadiseIslandApplication.class);
			String homeDir = home.getDir().getPath();
			String fullPathToSlashReplace = homeDir + mainImagePathByRoomId;
			String fullPath = fullPathToSlashReplace.replace("\\", "/");
			file = new File(fullPath);
		}
		byte[] bytes = Files.readAllBytes(file.toPath());
		return bytes;
	}

	private String findMainImagePathFromRoom(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		List<RoomImage> roomImages = getRoomImagesByOptionalRoom(room);
		String imagePath = "";
		if (roomImages.size() > 1) {
			throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, 
					"Too many main image downloaded");
		} else if (roomImages.size() == 1) {
			List<RoomImage> mainImage = roomImages.stream()
					.filter(findMainImage -> findMainImage.isMainImage() == true)
					.collect(Collectors.toList());
			imagePath = mainImage.get(0).getImagePath();
		} else {
			imagePath = RoomImageService.DEFAULT_IMAGE_PATH;
		}
		return imagePath;
	}
	
	private List<RoomImage> getRoomImagesByOptionalRoom(Optional<Room> room) {
		if (room.isPresent()) {
			List<RoomImage> roomImages = room.get().getRoomImages();
			roomImages = roomImages.stream()
					.filter(roomImg -> roomImg.isMainImage() == true)
					.collect(Collectors.toList());
			return roomImages;
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Downloading object failed");
	}
	
	public RoomDto getRoomDtoByOfferBooking(OfferBooking offerBooking) {
		Room room = offerBooking.getHotelBooking().getRoom();
		return roomMapper.toDto(room);
	}

	RoomDto save(RoomDto roomDto) {
		return mapAndSaveRoom(roomDto);
	}

	RoomDto update(RoomDto roomDto) {
		return mapAndSaveRoom(roomDto);
	}
	
	public void delete(Long id) {
		roomRepository.deleteById(id);
	}

	private RoomDto mapAndSaveRoom(RoomDto roomDto) {
		Room roomEntity = roomMapper.toEntity(roomDto);
		Room savedRoom = roomRepository.save(roomEntity);
		return roomMapper.toDto(savedRoom);
	}
}
