package pl.example.components.offer.hotel.room.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomCategoryService {

	RoomCategoryRepository roomCategoryRepository;

	@Autowired
	public RoomCategoryService(RoomCategoryRepository roomCategoryRepository) {
		this.roomCategoryRepository = roomCategoryRepository;
	}

	Optional<RoomCategoryDto> findById(Long id) {
		return roomCategoryRepository.findById(id)
				.map(RoomCategoryMapper::toDto);
	}

	List<RoomCategoryDto> findAll() {
		return roomCategoryRepository.findAllByOrderByIdAsc()
				.stream()
				.map(RoomCategoryMapper::toDto)
				.collect(Collectors.toList());
	}

	List<RoomCategoryDto> findByName(String name) {
		return roomCategoryRepository.findAllByNameContainingIgnoreCaseOrderByIdAsc(name)
				.stream()
				.map(RoomCategoryMapper::toDto)
				.collect(Collectors.toList());
	}

	List<String> findAllNames() {
		return roomCategoryRepository.findAll()
				.stream()
				.map(RoomCategory::getName)
				.collect(Collectors.toList());
	}

	RoomCategoryDto save(RoomCategoryDto roomCategory) {
		Optional<RoomCategory> roomCategoryByName = roomCategoryRepository
				.findByName(roomCategory.getName());
		roomCategoryByName.ifPresent(u -> {
			throw new DuplicateNameException();
		});
		return mapAndSaveRoomCategory(roomCategory);
	}

	RoomCategoryDto update(RoomCategoryDto roomCategory) {
		Optional<RoomCategory> roomCategoryByName = roomCategoryRepository
				.findByName(roomCategory.getName());
		roomCategoryByName.ifPresent(u -> {
			if (!u.getId().equals(roomCategory.getId()))
				throw new DuplicateNameException();
		});
		String unmodifiableMsg = "You cannot change this category.";
		isUnmodifiableRoomCategory(roomCategory.getId(), unmodifiableMsg);
		return mapAndSaveRoomCategory(roomCategory);
	}
	
	public void delete(Long id) {
		String unmodifiableMsg = "You cannot delete this category.";
		isUnmodifiableRoomCategory(id, unmodifiableMsg);
		roomCategoryRepository.deleteById(id);
	}

	private RoomCategoryDto mapAndSaveRoomCategory(RoomCategoryDto roomCategory) {
		RoomCategory roomCategoryEntity = RoomCategoryMapper.toEntity(roomCategory);
		RoomCategory savedRoomCategory = roomCategoryRepository.save(roomCategoryEntity);
		return RoomCategoryMapper.toDto(savedRoomCategory);
	}

	private void isUnmodifiableRoomCategory(Long id, String unmodifiableMsg) {
		if (id == 1 || id == 2 || id == 3 || id == 4) {
			throw new ResponseStatusException(HttpStatus.LOCKED, unmodifiableMsg
					+ " ('Standard', 'High Standard', 'Exclusive' and 'Apartment'"
					+ " modifications or delete are not possible)");
		}
	}
}
