package pl.example.components.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {

	List<String> fields = new ArrayList<>();
	List<String> messages = new ArrayList<>();
	
	public ValidationError(List<String> fields, List<String> messages) {
		this.fields = fields;
		this.messages = messages;
	}
	
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
