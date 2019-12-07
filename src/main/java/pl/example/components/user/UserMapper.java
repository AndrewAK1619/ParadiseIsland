package pl.example.components.user;

import pl.example.components.user.User;
import pl.example.components.user.UserDto;

public class UserMapper {

    static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMobilePhone(user.getMobilePhone());
        dto.setEmail(user.getEmail());
        dto.setPesel(user.getPesel());
        dto.setPassword(user.getPassword());
        return dto;
    }

    static User toEntity(UserDto user) {
        User entity = new User();
        entity.setId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setMobilePhone(user.getMobilePhone());
        entity.setEmail(user.getEmail());
        entity.setPesel(user.getPesel());
        entity.setPassword(user.getPassword());
        return entity;
    }
	
}
