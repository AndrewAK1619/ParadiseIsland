package pl.example.components.offer.hotel;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.example.components.offer.location.city.City;
import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.region.Region;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	Optional<Hotel> findById(Long hotelId);

	Page<Hotel> findById(Long hotelId, Pageable pageable);

	@Query(value = "SELECT * FROM hotels", nativeQuery = true)
	Page<Hotel> findAllHotel(Pageable pageable);

	@Query(value = "SELECT * FROM hotels hotel_id \r\n" + 
			"WHERE LOWER(hotel_id.hotel_name) LIKE LOWER(CONCAT('%', :hotelName, '%')) \r\n" + 
			"AND hotel_id.country_id IN (SELECT c.country_id FROM countries c \r\n" + 
			"WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :countryName, '%')))", nativeQuery = true)
	Page<Hotel> findAllByHotelNameAndCountryName(String hotelName, String countryName, Pageable pageable);
	
	Page<Hotel> findAllByCountry(Country country, Pageable pageable);
	Page<Hotel> findAllByRegion(Region region, Pageable pageable);
	Page<Hotel> findAllByCity(City city, Pageable pageable);
}
