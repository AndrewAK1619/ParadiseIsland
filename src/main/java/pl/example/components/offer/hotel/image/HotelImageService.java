package pl.example.components.offer.hotel.image;

import org.springframework.beans.factory.annotation.Autowired;

public class HotelImageService {

	HotelImageRepository imageRepository;
	
	@Autowired
	public HotelImageService(HotelImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}
}
