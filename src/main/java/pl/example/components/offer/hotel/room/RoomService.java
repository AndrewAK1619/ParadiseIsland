package pl.example.components.offer.hotel.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.room.category.RoomCategory;
import pl.example.components.offer.hotel.room.category.RoomCategoryRepository;

@Service
public class RoomService {

	RoomRepository roomRepository;
	RoomMapper roomMapper;
	RoomCategoryRepository roomCategoryRepository;

	@Autowired
	public RoomService(RoomRepository roomRepository, RoomMapper roomMapper,
			RoomCategoryRepository roomCategoryRepository) {
		this.roomRepository = roomRepository;
		this.roomMapper = roomMapper;
		this.roomCategoryRepository = roomCategoryRepository;
	}

	List<RoomDto> findAll() {
		return roomRepository.findAll()
				.stream()
				.map(roomMapper::toDto)
				.collect(Collectors.toList());
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

	List<RoomDto> findAllByRoomCategory(String roomCategoryName) {
		List<RoomCategory> roomCategoryList = roomCategoryRepository
				.findAllByNameContainingIgnoreCaseOrderByIdAsc(roomCategoryName);
		
		List<RoomDto> listRoomDto = new ArrayList<>();

		for (RoomCategory roomCategoty : roomCategoryList) {
			listRoomDto.addAll((roomRepository.findAllByRoomCategory(roomCategoty)
					.stream()
					.map(roomMapper::toDto)
					.collect(Collectors.toList())));
		}
		listRoomDto.sort((a,  b) -> a.getId().compareTo(b.getId()));
		return listRoomDto;
	}
	
    Optional<RoomDto> findById(Long id) {
        return roomRepository.findById(id).map(roomMapper::toDto);
    }
	
    RoomDto save(RoomDto roomDto) {
    	return mapAndSaveRoom(roomDto);
    }
    
    RoomDto update(RoomDto roomDto) {
        return mapAndSaveRoom(roomDto);
    }
    
    private RoomDto mapAndSaveRoom(RoomDto asset) {
    	Room roomEntity = roomMapper.toEntity(asset);
    	Room savedAsset = roomRepository.save(roomEntity);
        return roomMapper.toDto(savedAsset);
    }
}
