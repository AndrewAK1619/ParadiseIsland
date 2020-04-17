package pl.example.components.offer.hotel;

import java.util.HashSet;
import java.util.Set;

import pl.example.components.offer.hotel.room.Room;

public class HotelDto {

	private Long id;
	private String hotelName;
	private String description;
	private Set<Room> room = new HashSet<>();
	private Long mainImageId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Room> getRoom() {
		return room;
	}
	public void setRoom(Set<Room> room) {
		this.room = room;
	}
	public Long getMainImageId() {
		return mainImageId;
	}
	public void setMainImageId(Long mainImageId) {
		this.mainImageId = mainImageId;
	}
}
