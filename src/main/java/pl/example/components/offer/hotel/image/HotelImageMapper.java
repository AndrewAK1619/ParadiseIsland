package pl.example.components.offer.hotel.image;

public class HotelImageMapper {

	HotelImageDto toDto(HotelImage hotelImage) {
		HotelImageDto hotelImageDto = new HotelImageDto();
		hotelImageDto.setId(hotelImage.getId());
		hotelImageDto.setImagePath(hotelImage.getImagePath());
		hotelImageDto.setMainImage(hotelImage.isMainImage());
		return hotelImageDto;
	}
	
	HotelImage toEntity(HotelImageDto hotelImageDto) {
		HotelImage hotelImage = new HotelImage();
		hotelImage.setId(hotelImageDto.getId());
		hotelImage.setImagePath(hotelImageDto.getImagePath());
		hotelImage.setMainImage(hotelImageDto.isMainImage());
		return hotelImage;
	}
}
