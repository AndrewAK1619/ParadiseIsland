package pl.example.components.offer.hotel.advantages;

public class HotelAdvantageDto {

	private Long id;
	private String descriptionAdvantage;
	private Long hotelId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescriptionAdvantage() {
		return descriptionAdvantage;
	}
	public void setDescriptionAdvantage(String descriptionAdvantage) {
		this.descriptionAdvantage = descriptionAdvantage;
	}
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
}
