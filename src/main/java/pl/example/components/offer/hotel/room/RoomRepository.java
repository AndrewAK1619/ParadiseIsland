package pl.example.components.offer.hotel.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.example.components.offer.hotel.room.category.RoomCategory;

public interface RoomRepository extends JpaRepository<Room, Long> {

	List<Room> findAllByHotelId(long hotelId);
	List<Room> findAllByHotelIdAndRoomCategory(long hotelId, RoomCategory roomCategory);
	List<Room> findAllByNumberOfSingleBeds(int numberOfBeds);
	List<Room> findAllByNumberOfDoubleBeds(int numberOfBeds);
	
	@Query(value = "SELECT * FROM rooms r										\r\n" + 
			"WHERE r.hotel_id = :hotelId 										\r\n" + 
			"AND NOT EXISTS(													\r\n" + 
			"	SELECT * FROM hotel_booking hb 									\r\n" + 
			"	WHERE hb.room_id = r.room_id									\r\n" + 
			"	AND (:departure < hb.end_booking_room							\r\n" + 
			"		AND :returnDate > hb.start_booking_room))					\r\n" + 
			"AND (number_of_double_beds*2) + number_of_single_beds >= :persons	\r\n" + 
			"ORDER BY r.room_price;", nativeQuery = true)
	List<Room> findAvailableRooms(Long hotelId, LocalDate departure, LocalDate returnDate, Integer persons);
}
