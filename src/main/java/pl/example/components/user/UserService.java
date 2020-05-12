package pl.example.components.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.example.components.user.UserRepository;
import pl.example.components.user.role.UserRole;
import pl.example.components.user.role.UserRoleRepository;
import pl.example.components.user.DuplicateEmailException;
import pl.example.components.user.User;
import pl.example.components.user.UserDto;
import pl.example.components.user.UserMapper;

@Service
public class UserService {

	private static final String DEFAULT_ROLE = "ROLE_USER";
	
    private UserRepository userRepository;
	private UserRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository,
    		UserRoleRepository roleRepository,
    		PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
	
    Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDto);
    }
    
    List<UserDto> findAll() {
        return userRepository.findAllByOrderByIdAsc()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
    
    List<UserDto> findByLastName(String lastName) {
        return userRepository.findAllByLastNameContainingIgnoreCaseOrderByIdAsc(lastName)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
    
    UserDto save(UserDto user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        userByEmail.ifPresent(u -> {
            throw new DuplicateEmailException();
        });
        return mapAndSaveUser(user);
    }
    
    UserDto update(UserDto user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        userByEmail.ifPresent(u -> {
            if(!u.getId().equals(user.getId()))
                throw new DuplicateEmailException();
        });
        return mapAndSaveUser(user);
    }
    
    private UserDto mapAndSaveUser(UserDto user) {
        User userEntity = UserMapper.toEntity(user);
        addWithDefaultRole(userEntity);
        User savedUser = userRepository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }
    
	public void addWithDefaultRole(User user) {
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		String passwordHash = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordHash);
		userRepository.save(user);
	}
}
