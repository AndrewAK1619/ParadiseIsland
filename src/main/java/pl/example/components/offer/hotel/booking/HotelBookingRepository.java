package pl.example.components.offer.hotel.booking;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {

	Optional<HotelBooking> findById(long hotelId);
}
