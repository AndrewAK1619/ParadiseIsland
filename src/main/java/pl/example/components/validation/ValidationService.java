package pl.example.components.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ValidationService {

	public static ValidationError valid(BindingResult result) {
		List<ObjectError> objectErrors = result.getAllErrors();
		List<FieldError> fieldErrors = new ArrayList<>();
		objectErrors.forEach(err -> fieldErrors.add((FieldError) err));
		List<String> fields = new ArrayList<>();
		List<String> messages = new ArrayList<>();
		fieldErrors.forEach(e -> fields.add(e.getField()));
		fieldErrors.forEach(e -> messages.add(e.getDefaultMessage()));
		return new ValidationError(fields, messages);
	}
}
