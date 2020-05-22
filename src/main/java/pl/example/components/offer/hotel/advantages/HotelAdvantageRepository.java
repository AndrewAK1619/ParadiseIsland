package pl.example.components.offer.hotel.advantages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelAdvantageRepository extends JpaRepository<HotelAdvantage, Long> {


	/* Metods only for put example data / Lorem Ipsum ect. */

	@Query(value = "SELECT * FROM hotel_advantages WHERE hotel_id IS NULL ORDER BY hotel_advantage_id LIMIT 6", nativeQuery = true)
	List<HotelAdvantage> findDefaultAdvantages();
}
