package pl.example.components.offer.hotel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class HotelDto {

	private Long id;
	@NotBlank(message="{offer.hotel.hotelName.NotBlank}")
	@Size(max = 30, message="{offer.hotel.hotelName.Size}")
	private String hotelName;
	@Size(max = 150, message="{offer.hotel.description.Size}")
	private String description;
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
	public Long getMainImageId() {
		return mainImageId;
	}
	public void setMainImageId(Long mainImageId) {
		this.mainImageId = mainImageId;
	}
}
