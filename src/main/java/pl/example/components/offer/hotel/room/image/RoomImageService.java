package pl.example.components.offer.hotel.room.image;

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
public class RoomImageService {

	public static final String DEFAULT_IMAGE_PATH = 
			"/static/img/default/room.jpg";

	RoomImageRepository imageRepository;
	RoomImageMapper roomImageMapper;

	@Autowired
	public RoomImageService(
			RoomImageRepository imageRepository,
			RoomImageMapper roomImageMapper) {
		this.imageRepository = imageRepository;
		this.roomImageMapper = roomImageMapper;
	}

	public RoomImageDto saveRoomImage(MultipartFile file, Long roomId) {
		String newFileName = createIndividualFileImageName(file);
		if(newFileName == null)
			return null;
		String pathImage = getNewPathRoomImage(newFileName);
		saveImageFileToServer(file, pathImage);
		RoomImageDto roomImageDto = createImageDtoWithMainImg(pathImage, roomId);
		RoomImageDto roomImageDtoSave = save(roomImageDto);
		return roomImageDtoSave;
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
	
	private String getNewPathRoomImage(String newFileName) {
		return "/storage/images/rooms/" + newFileName;
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

	private RoomImageDto createImageDtoWithMainImg(String pathImage, Long roomId) {
		RoomImageDto roomImageDto = new RoomImageDto();
		changeValueOfTheCurrentMainImage(roomId);
		roomImageDto.setImagePath(pathImage);
		roomImageDto.setMainImage(true);
		roomImageDto.setRoomId(roomId);
		return roomImageDto;
	}
	
	private void changeValueOfTheCurrentMainImage(Long roomId) {
		Optional<RoomImage> currentMainRoomImage = imageRepository.findMainImageByRoomId(roomId);
		if(currentMainRoomImage.isPresent()) {
			RoomImage roomImage = currentMainRoomImage.get();
			roomImage.setMainImage(false);
			imageRepository.save(roomImage);
		}
	}

	private RoomImageDto save(RoomImageDto roomImageDto) {
		return mapAndSaveRoom(roomImageDto);
	}

	RoomImageDto update(RoomImageDto roomImageDto) {
		return mapAndSaveRoom(roomImageDto);
	}

	private RoomImageDto mapAndSaveRoom(RoomImageDto roomImageDto) {
		RoomImage roomImageEntity = roomImageMapper.toEntity(roomImageDto);
		RoomImage saveRoomImageEntity = imageRepository.save(roomImageEntity);
		return RoomImageMapper.toDto(saveRoomImageEntity);
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
