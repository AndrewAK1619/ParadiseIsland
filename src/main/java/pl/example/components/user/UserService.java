package pl.example.components.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
		return userRepository
				.findById(id)
				.map(UserMapper::toDto);
	}

	Optional<UserDto> findByEmail(String email) {
		return userRepository
				.findByEmail(email)
				.map(UserMapper::toDto);
	}
	
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
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

	UserDto changeUserEmial(String newEmail) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String actualEmail = authentication.getName();

		if (actualEmail.equals("admin@example.com") || actualEmail.equals("user@example.com")) {
			throw new ResponseStatusException(HttpStatus.LOCKED, 
					"You cannot change this user's email. "
					+ "('admin@example.com' and 'user@example.com' modifications are not possible)");
		}
		if (actualEmail.equals(newEmail)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"The user with the given e-mail already exists. Enter a different e-mail");
		}
		Optional<User> userByEmail = userRepository.findByEmail(newEmail);
		userByEmail.ifPresent(u -> {
			throw new DuplicateEmailException();
		});
		Optional<User> userOpt = userRepository.findByEmail(actualEmail);
		if (userOpt.isPresent()) {
			UserDto userDto = UserMapper.toDto(userOpt.get());
			userDto.setEmail(newEmail);
			return mapAndSaveUser(userDto, false);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"The user is not found");
		}
	}

	UserDto changeUserPassword(String oldPassword, String newPassword) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String actualEmail = authentication.getName();

		if (actualEmail.equals("admin@example.com") || actualEmail.equals("user@example.com")) {
			throw new ResponseStatusException(HttpStatus.LOCKED, 
					"You cannot change this user's password. "
					+ "('admin@example.com' and 'user@example.com' modifications are not possible)");
		}
		Optional<User> userOpt = userRepository.findByEmail(actualEmail);
		if (userOpt.isPresent()) {
			UserDto userDto = UserMapper.toDto(userOpt.get());

			if (!passwordEncoder.matches(oldPassword, userDto.getPassword())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, 
						"The old password is incorrect. Try again");
			}
			if (oldPassword.equals(newPassword)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"The old password and the new one are the same. Enter a different password");
			}
			String newPasswordHashed = passwordEncoder.encode(newPassword);
			userDto.setPassword(newPasswordHashed);
			
			return mapAndSaveUser(userDto, false);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"The user is not found");
		}
	}
	
	UserDto save(UserDto user) {
		Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
		userByEmail.ifPresent(u -> {
			throw new DuplicateEmailException();
		});
		return mapAndSaveUser(user, true);
	}

	UserDto update(UserDto user) {
		Optional<User> userById = userRepository.findById(user.getId());
		userById.ifPresent(u -> {
			if (!u.getId().equals(user.getId()))
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
						"The user is not found");
		});
		return mapAndSaveUser(user, false);
	}

	private UserDto mapAndSaveUser(UserDto user, boolean isUserNotExist) {
		User userEntity = UserMapper.toEntity(user);
		if (isUserNotExist) {
			addWithDefaultRole(userEntity);
		} else {
			Optional<User> userById = userRepository.findById(user.getId());
			userById.ifPresent(u -> {
				userEntity.setRoles(u.getRoles());
			});
		}
		User savedUser = userRepository.save(userEntity);
		return UserMapper.toDto(savedUser);
	}

	private void addWithDefaultRole(User user) {
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		String passwordHash = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordHash);
	}
}
