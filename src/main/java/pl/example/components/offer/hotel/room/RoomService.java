package pl.example.components.offer.hotel.room;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
					.findAllByHotelIdAndRoomCategory(hotelId, roomCategory)
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
	
	List<byte[]> getMainImgListInByteByRoomDtoList(List<RoomDto> roomDtoList) {
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
		File file = new File(findMainImagePathFromRoom(roomId));
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

	RoomDto save(RoomDto roomDto) {
		return mapAndSaveRoom(roomDto);
	}

	RoomDto update(RoomDto roomDto) {
		return mapAndSaveRoom(roomDto);
	}

	private RoomDto mapAndSaveRoom(RoomDto roomDto) {
		Room roomEntity = roomMapper.toEntity(roomDto);
		Room savedRoom = roomRepository.save(roomEntity);
		return roomMapper.toDto(savedRoom);
	}
}
