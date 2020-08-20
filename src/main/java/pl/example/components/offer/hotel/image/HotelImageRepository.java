package pl.example.components.offer.hotel.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelImageRepository extends JpaRepository<HotelImage, Long> {

	@Query(value = "SELECT * FROM hotel_images hi "
			+ "WHERE hi.hotel_id = :hotelId AND hi.top_image = 1;", nativeQuery = true)
	Optional<HotelImage> findMainImageByHotelId(Long hotelId);
}
