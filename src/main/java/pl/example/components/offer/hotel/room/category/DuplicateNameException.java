package pl.example.components.offer.hotel.room.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, 
	reason = "A room categoty with this Name already exists")
public class DuplicateNameException extends RuntimeException {
}
