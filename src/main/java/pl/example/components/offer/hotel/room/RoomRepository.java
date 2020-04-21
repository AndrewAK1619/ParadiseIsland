package pl.example.components.offer.hotel.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.example.components.offer.hotel.room.category.RoomCategory;

public interface RoomRepository extends JpaRepository<Room, Long> {
	
	List<Room> findAllByNumberOfSingleBeds(int numberOfBeds);
	List<Room> findAllByNumberOfDoubleBeds(int numberOfBeds);
	List<Room> findAllByHotelId(long hotelId);
	List<Room> findAllByHotelIdAndRoomCategory(long hotelId, RoomCategory roomCategory);
}
