package pl.example.components.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "A user with this e-mail already exists")
class DuplicateEmailException extends RuntimeException {

}