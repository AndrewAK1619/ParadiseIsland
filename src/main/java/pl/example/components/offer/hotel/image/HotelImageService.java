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
		String newFileName = createIndividualFileImageName(file);
		if(newFileName == null)
			return null;
		String pathImage = getNewPathHotelImage(newFileName);
		saveImageFileToServer(file, pathImage);
		HotelImageDto hotelImageDto = createImageDtoWithMainImg(pathImage);
		HotelImageDto hotelImageDtoSave = save(hotelImageDto);
		return hotelImageDtoSave;
	}
	
	private String createIndividualFileImageName(MultipartFile file) {
		int year = LocalDate.now().getYear();
		int month = LocalDate.now().getMonthValue();
		int day = LocalDate.now().getDayOfMonth();
		long second = Instant.now().getEpochSecond();
		String oryginalFileName = "";
		String newFileName = "";

		if(file != null) {
			oryginalFileName = file.getOriginalFilename();
			newFileName = year + "" + month + "" + day + "" + second + "."
					+ getExtensionByStringHandling(oryginalFileName).toString();
		} else {
			return null;
		}
		return newFileName;
	}
	
	private String getExtensionByStringHandling(String fileName) {
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}
		return extension;
	}

	private String getNewPathHotelImage(String newFileName) {
		return "src/main/resources/static/img/hotels/" + newFileName;
	}
	
	private void saveImageFileToServer(MultipartFile file, String pathImage) {
		try {
			byte[] bytes = file.getBytes();
			BufferedOutputStream bufferOutputStream = new BufferedOutputStream(
					new FileOutputStream(new File(pathImage)));
			bufferOutputStream.write(bytes);
			bufferOutputStream.close();
		} catch (IOException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Unable to load file: " + file.getOriginalFilename());
		}
	}

	private HotelImageDto createImageDtoWithMainImg(String pathImage) {
		HotelImageDto hotelImageDto = new HotelImageDto();
		hotelImageDto.setImagePath(pathImage);
		hotelImageDto.setMainImage(true);
		return hotelImageDto;
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
