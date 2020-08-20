package pl.example.components.offer.hotel.room.image;

public class RoomImageDto {

	private Long id;
	private String imagePath;
	private boolean mainImage;
	private Long roomId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isMainImage() {
		return mainImage;
	}
	public void setMainImage(boolean mainImage) {
		this.mainImage = mainImage;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
}
