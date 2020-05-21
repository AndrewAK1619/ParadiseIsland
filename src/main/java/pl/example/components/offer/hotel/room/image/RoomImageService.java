package pl.example.components.offer.hotel.room.image;

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
public class RoomImageService {

	public static final String DEFAULT_IMAGE_PATH = 
			"src/main/resources/static/img/default/room.jpg";

	RoomImageRepository imageRepository;

	@Autowired
	public RoomImageService(RoomImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public RoomImageDto saveRoomImage(MultipartFile file) {
		String newFileName = createIndividualFileImageName(file);
		if(newFileName == null)
			return null;
		String pathImage = getNewPathRoomImage(newFileName);
		saveImageFileToServer(file, pathImage);
		RoomImageDto roomImageDto = createImageDtoWithMainImg(pathImage);
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
		return "src/main/resources/static/img/rooms/" + newFileName;
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
	
	private RoomImageDto createImageDtoWithMainImg(String pathImage) {
		RoomImageDto roomImageDto = new RoomImageDto();
		roomImageDto.setImagePath(pathImage);
		roomImageDto.setMainImage(true);
		return roomImageDto;
	}

	private RoomImageDto save(RoomImageDto roomImageDto) {
		return mapAndSaveRoom(roomImageDto);
	}

	RoomImageDto update(RoomImageDto roomImageDto) {
		return mapAndSaveRoom(roomImageDto);
	}

	private RoomImageDto mapAndSaveRoom(RoomImageDto roomImageDto) {
		RoomImage roomImageEntity = RoomImageMapper.toEntity(roomImageDto);
		RoomImage saveRoomImageEntity = imageRepository.save(roomImageEntity);
		return RoomImageMapper.toDto(saveRoomImageEntity);
	}

	public byte[] getDefaultMainImageInByte() throws IOException {
		File file = new File(DEFAULT_IMAGE_PATH);
		byte[] bytes = Files.readAllBytes(file.toPath());
		return bytes;
	}
}
