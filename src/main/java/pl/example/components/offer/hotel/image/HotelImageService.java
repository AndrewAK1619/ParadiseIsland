package pl.example.components.offer.hotel.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import pl.example.ParadiseIslandApplication;

@Service
public class HotelImageService {

	public static final String DEFAULT_IMAGE_PATH = 
			"/static/img/default/hotel.jpg";

	private HotelImageRepository imageRepository;
	private HotelImageMapper hotelImageMapper;

	@Autowired
	public HotelImageService(
			HotelImageRepository imageRepository,
			HotelImageMapper hotelImageMapper) {
		this.imageRepository = imageRepository;
		this.hotelImageMapper = hotelImageMapper;
	}

	public HotelImageDto saveHotelImage(MultipartFile file, Long hotelId) {
		String newFileName = createIndividualFileImageName(file);
		if(newFileName == null)
			return null;
		String pathImage = getNewPathHotelImage(newFileName);
		saveImageFileToServer(file, pathImage);
		HotelImageDto hotelImageDto = createImageDtoWithMainImg(pathImage, hotelId);
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
		return "/storage/images/hotels/" + newFileName;
	}

	private void saveImageFileToServer(MultipartFile file, String pathImage) {
		ApplicationHome home = new ApplicationHome(ParadiseIslandApplication.class);
		String homeDir = home.getDir().getPath();
		String fullPathToSlashReplace = homeDir + pathImage;
		String fullPath = fullPathToSlashReplace.replace("\\", "/");
		try {
			byte[] bytes = file.getBytes();
			BufferedOutputStream bufferOutputStream = new BufferedOutputStream(
					new FileOutputStream(new File(fullPath)));
			bufferOutputStream.write(bytes);
			bufferOutputStream.close();
		} catch (IOException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Unable to load file: " + file.getOriginalFilename());
		}
	}

	private HotelImageDto createImageDtoWithMainImg(String pathImage, Long hotelId) {
		HotelImageDto hotelImageDto = new HotelImageDto();
		changeValueOfTheCurrentMainImage(hotelId);
		hotelImageDto.setImagePath(pathImage);
		hotelImageDto.setMainImage(true);
		hotelImageDto.setHotelId(hotelId);
		return hotelImageDto;
	}

	private void changeValueOfTheCurrentMainImage(Long hotelId) {
		Optional<HotelImage> currentMainHotelImage = imageRepository.findMainImageByHotelId(hotelId);
		if(currentMainHotelImage.isPresent()) {
			HotelImage hotelImage = currentMainHotelImage.get();
			hotelImage.setMainImage(false);
			imageRepository.save(hotelImage);
		}
	}

	private HotelImageDto save(HotelImageDto hotelImageDto) {
		return mapAndSaveHotel(hotelImageDto);
	}

	HotelImageDto update(HotelImageDto hotelImageDto) {
		return mapAndSaveHotel(hotelImageDto);
	}

	private HotelImageDto mapAndSaveHotel(HotelImageDto hotelImageDto) {
		HotelImage hotelImageEntity = hotelImageMapper.toEntity(hotelImageDto);
		HotelImage saveHotelImageEntity = imageRepository.save(hotelImageEntity);
		return HotelImageMapper.toDto(saveHotelImageEntity);
	}

	public byte[] getDefaultMainImageInByte() throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(DEFAULT_IMAGE_PATH);
		InputStream inputStream = classPathResource.getInputStream();
		File file = File.createTempFile("test", ".jpg");
		FileUtils.copyInputStreamToFile(inputStream, file);
		byte[] bytes = Files.readAllBytes(file.toPath());
		return bytes;
	}
}
