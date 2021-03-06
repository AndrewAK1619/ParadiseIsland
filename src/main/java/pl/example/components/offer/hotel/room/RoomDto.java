package pl.example.components.offer.hotel.room;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class RoomDto {

	private Long id;
	@NotNull(message = "{offer.hotel.room.numberOfSingleBeds.NotNull}")
	@Range(min = 0, max = 10, message = "{offer.hotel.room.numberOfSingleBeds.Range}")
	private int numberOfSingleBeds;
	@NotNull(message = "{offer.hotel.room.numberOfDoubleBeds.NotNull}")
	@Range(min = 0, max = 10, message = "{offer.hotel.room.numberOfDoubleBeds.Range}")
	private int numberOfDoubleBeds;
	@NotNull(message = "{offer.hotel.room.roomPrice.NotNull}")
	@Range(min = 50, max = 2000, message = "{offer.hotel.room.roomPrice.Range}")
	private int roomPrice;
	@NotBlank(message = "{offer.hotel.room.roomCategory.NotBlank}")
	private String roomCategory;
	private Long mainImageId;
	private Long hotelId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNumberOfSingleBeds() {
		return numberOfSingleBeds;
	}
	public void setNumberOfSingleBeds(int numberOfSingleBeds) {
		this.numberOfSingleBeds = numberOfSingleBeds;
	}
	public int getNumberOfDoubleBeds() {
		return numberOfDoubleBeds;
	}
	public void setNumberOfDoubleBeds(int numberOfDoubleBeds) {
		this.numberOfDoubleBeds = numberOfDoubleBeds;
	}
	public int getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}
	public String getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}
	public Long getMainImageId() {
		return mainImageId;
	}
	public void setMainImageId(Long mainImageId) {
		this.mainImageId = mainImageId;
	}
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
}
