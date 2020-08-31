package pl.example.components.user;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.example.components.security.CustomUserDetailsService;
import pl.example.components.user.UserController;
import pl.example.components.security.jwt.util.JwtUtil;

@WebMvcTest(UserController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
class UserControllerTest {

	@Autowired
	private UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private JwtUtil jwtUtil;

	private UserDto userDto;

	@BeforeEach
	void setUp() {
		userDto = new UserDto();
		userDto.setFirstName("John");
		userDto.setLastName("Smith");
		userDto.setMobilePhone("111222333");
		userDto.setEmail("example@example.com");
		userDto.setPassword("securitysecuritysecurity");
	}

	@Test
	void findAllTest() throws Exception {
		UserDto userDtoNumberTwo = new UserDto();
		userDtoNumberTwo.setFirstName("Will");
		userDtoNumberTwo.setLastName("Johnson");
		userDtoNumberTwo.setMobilePhone("444555666");
		userDtoNumberTwo.setEmail("user@example.com");

		List<UserDto> userDtoList = new ArrayList<>();
		userDtoList.add(userDto);
		userDtoList.add(userDtoNumberTwo);

		when(userService.findAll()).thenReturn(userDtoList);

		mockMvc.perform(get("/admin/users"))
				.andDo(print())
				.andExpect(jsonPath("$[0].firstName").value("John"))
				.andExpect(jsonPath("$[0].lastName").value("Smith"))
				.andExpect(jsonPath("$[0].mobilePhone").value("111222333"))
				.andExpect(jsonPath("$[0].email").value("example@example.com"))
				.andExpect(jsonPath("$[1].firstName").value("Will"))
				.andExpect(jsonPath("$[1].lastName").value("Johnson"))
				.andExpect(jsonPath("$[1].mobilePhone").value("444555666"))
				.andExpect(jsonPath("$[1].email").value("user@example.com"))
				.andExpect(status().isOk());
	}

	@Test
	void saveTest() throws Exception {
		when(userService.save(any(UserDto.class))).thenReturn(userDto);

		mockMvc.perform(post("/admin/users")
				.content(new ObjectMapper().writeValueAsString(userDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Smith"))
				.andExpect(jsonPath("$.mobilePhone").value("111222333"))
				.andExpect(jsonPath("$.email").value("example@example.com"))
				.andExpect(status().isCreated());
	}

	@Test
	void findByIdTest() throws Exception {
		userDto.setId(12L);
		Optional<UserDto> userDtoOpt = Optional.of(userDto);
		when(userService.findById(anyLong())).thenReturn(userDtoOpt);

		mockMvc.perform(get("/admin/users/12"))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Smith"))
				.andExpect(jsonPath("$.mobilePhone").value("111222333"))
				.andExpect(jsonPath("$.email").value("example@example.com"))
				.andExpect(status().isOk());
	}

	@Test
	void updateTest() throws Exception {
		userDto.setId(12L);
		when(userService.update(any(UserDto.class))).thenReturn(userDto);

		mockMvc.perform(put("/admin/users/12")
				.content(new ObjectMapper().writeValueAsString(userDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Smith"))
				.andExpect(jsonPath("$.mobilePhone").value("111222333"))
				.andExpect(jsonPath("$.email").value("example@example.com"))
				.andExpect(status().isOk());
	}

	@Test
	void deleteTest() throws Exception {
		mockMvc.perform(delete("/admin/users/12"))
				.andExpect(status().isOk());
	}
}
