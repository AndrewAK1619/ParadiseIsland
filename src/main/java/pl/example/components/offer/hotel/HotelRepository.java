package pl.example.components.offer.hotel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

	List<Hotel> findAllByHotelNameContainingIgnoreCase(String hotelName);
}