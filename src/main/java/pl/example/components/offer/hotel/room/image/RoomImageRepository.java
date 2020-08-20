package pl.example.components.offer.hotel.room.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

	@Query(value = "SELECT * FROM room_images ri "
			+ "WHERE ri.room_id = :roomId AND ri.top_image = 1;", nativeQuery = true)
	Optional<RoomImage> findMainImageByRoomId(Long roomId);
}
