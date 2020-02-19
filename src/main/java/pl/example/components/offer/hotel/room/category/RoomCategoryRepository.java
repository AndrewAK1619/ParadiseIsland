package pl.example.components.offer.hotel.room.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Long> {

	Optional<RoomCategory> findByName(String name);

	List<RoomCategory> findAllByOrderByIdAsc();
	List<RoomCategory> findAllByNameContainingIgnoreCaseOrderByIdAsc(String name);
}
