package pl.example.components.offer.hotel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	@Query(value = "SELECT * FROM hotels", nativeQuery = true)
	Page<Hotel> findAllHotel(Pageable pageable);

	@Query(value = "SELECT * FROM hotels hotel_id \r\n" + 
			"WHERE LOWER(hotel_id.hotel_name) LIKE LOWER(CONCAT('%', :hotelName, '%')) \r\n" + 
			"AND hotel_id.country_id IN (SELECT c.country_id FROM countries c \r\n" + 
			"WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :countryName, '%')))", nativeQuery = true)
	Page<Hotel> findAllByHotelNameAndCountryName(String hotelName, String countryName, Pageable pageable);
}
