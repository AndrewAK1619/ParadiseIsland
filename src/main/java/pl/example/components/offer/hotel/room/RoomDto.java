package pl.example.components.offer.hotel.room;

public class RoomDto {

	private Long id;
	private int numberOfSingleBeds;
	private int numberOfDoubleBeds;
	private int roomPrice;
	private String roomCategory;
	private Long mainImageId;
	
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
}
