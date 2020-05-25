package pl.example.components.offer.hotel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	List<Hotel> findAllByHotelNameContainingIgnoreCase(String hotelName);
	
	@Query(value = "SELECT * FROM hotels\r\n" + 
					"	ORDER BY RAND()\r\n" + 
					"	LIMIT 15", nativeQuery = true)
	List<Hotel> own();
}