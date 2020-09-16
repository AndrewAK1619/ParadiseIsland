package pl.example.components.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.example.components.security.CustomUserDetailsService;
import pl.example.components.security.jwt.util.JwtUtil;

@WebMvcTest(AccountController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
class AccountControllerTest {

	@Autowired
	private AccountController accountController;

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
	void setUp() throws Exception {
		userDto = new UserDto();
		userDto.setFirstName("John");
		userDto.setLastName("Smith");
		userDto.setMobilePhone("111222333");
		userDto.setEmail("example@example.com");
		userDto.setPassword("securitysecuritysecurity");
	}

	@Test
	void saveTest() throws Exception {
		when(userService.save(any(UserDto.class))).thenReturn(userDto);

		mockMvc.perform(post("/account/register")
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

		mockMvc.perform(get("/account/register/success/12"))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Smith"))
				.andExpect(jsonPath("$.mobilePhone").value("111222333"))
				.andExpect(jsonPath("$.email").value("example@example.com"))
				.andExpect(status().isOk());
	}

	@Test
	void saveUserEmailTest() throws Exception { 
		userDto.setEmail("new@example.com");
		when(userService.changeUserEmial(Mockito.anyString())).thenReturn(userDto);

		mockMvc.perform(post("/account/profile/email")
				.content(new ObjectMapper().writeValueAsString(userDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Smith"))
				.andExpect(jsonPath("$.mobilePhone").value("111222333"))
				.andExpect(jsonPath("$.email").value("new@example.com"))
				.andExpect(status().isOk());
	}

	@Test
	void updateUserDetailsTest() throws Exception {
		when(userService.update(any(UserDto.class))).thenReturn(userDto);

		mockMvc.perform(put("/account/profile/userDetails")
				.content(new ObjectMapper().writeValueAsString(userDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Smith"))
				.andExpect(jsonPath("$.mobilePhone").value("111222333"))
				.andExpect(jsonPath("$.email").value("example@example.com"))
				.andExpect(status().isOk());
	}
}
