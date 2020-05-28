package pl.example.components.offer.hotel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	@Query(value = "SELECT * FROM hotels", nativeQuery = true)
	Page<Hotel> findAllHotel(Pageable pageable);
	
	// TODO fix search
//	List<Hotel> findAllByHotelNameContainingIgnoreCase(String hotelName);
}