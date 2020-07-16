package pl.example.components.offer.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.example.components.user.User;

public interface OfferBookingRepository extends JpaRepository<OfferBooking, Long>{

	List<OfferBooking> findAllByUser(User user);
	Optional<OfferBooking> findByIdAndUser(Long id, User user);
}
