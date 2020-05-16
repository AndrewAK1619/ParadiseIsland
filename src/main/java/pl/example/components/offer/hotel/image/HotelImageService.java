package pl.example.components.offer.hotel.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class HotelImageService {

	public static final String DEFAULT_IMAGE_PATH = 
			"src/main/resources/static/img/default/hotel.jpg";

	private HotelImageRepository imageRepository;

	@Autowired
	public HotelImageService(HotelImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public HotelImageDto saveHotelImage(MultipartFile file) {
		HotelImageDto hotelImageDtoSave = null;
		if (file != null) {
			HotelImageDto hotelImageDto = createImageDto(file);
			hotelImageDtoSave = save(hotelImageDto);
		}
		return hotelImageDtoSave;
	}

	private HotelImageDto createImageDto(MultipartFile file) {
		String pathImage = createIndividualImageNameAndSaveImageToServer(file);
		HotelImageDto hotelImageDto = new HotelImageDto();
		hotelImageDto.setImagePath(pathImage);
		hotelImageDto.setMainImage(true);
		return hotelImageDto;
	}

	private String createIndividualImageNameAndSaveImageToServer(MultipartFile file) {
		int year = LocalDate.now().getYear();
		int month = LocalDate.now().getMonthValue();
		int day = LocalDate.now().getDayOfMonth();
		long second = Instant.now().getEpochSecond();
		String oryginalFileName = "";
		String newFileName = "";
		String pathImage = "";

		try {
			oryginalFileName = file.getOriginalFilename();
			newFileName = year + "" + month + "" + day + "" + second + "."
					+ getExtensionByStringHandling(oryginalFileName).toString();

			pathImage = "src/main/resources/static/img/hotels/" + newFileName;

			byte[] bytes = file.getBytes();
			BufferedOutputStream bufferOutputStream = new BufferedOutputStream(
					new FileOutputStream(new File(pathImage)));
			bufferOutputStream.write(bytes);
			bufferOutputStream.close();
		} catch (IOException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Unable to load file: " + oryginalFileName);
		}
		return pathImage;
	}

	private String getExtensionByStringHandling(String fileName) {
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

	private HotelImageDto save(HotelImageDto hotelImageDto) {
		return mapAndSaveHotel(hotelImageDto);
	}

	HotelImageDto update(HotelImageDto hotelImageDto) {
		return mapAndSaveHotel(hotelImageDto);
	}

	private HotelImageDto mapAndSaveHotel(HotelImageDto hotelImageDto) {
		HotelImage hotelImageEntity = HotelImageMapper.toEntity(hotelImageDto);
		HotelImage saveHotelImageEntity = imageRepository.save(hotelImageEntity);
		return HotelImageMapper.toDto(saveHotelImageEntity);
	}

	public byte[] getDefaultMainImageInByte() throws IOException {
		File file = new File(DEFAULT_IMAGE_PATH);
		byte[] bytes = Files.readAllBytes(file.toPath());
		return bytes;
	}
}
