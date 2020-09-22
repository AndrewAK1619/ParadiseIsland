package pl.example.components.offer.location.region;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import pl.example.components.security.CustomUserDetailsService;
import pl.example.components.security.jwt.util.JwtUtil;

@WebMvcTest(RegionController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
class RegionControllerTest {

	@Autowired
	private RegionController regionController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RegionService regionService;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private JwtUtil jwtUtil;

	private RegionDto regionDto;

	@BeforeEach
	void setUp() throws Exception {
		regionDto = new RegionDto();
		regionDto.setName("Masovia");
		regionDto.setCountryName("Poland");
	}

	@Test
	void findAllNamesTest() throws Exception {
		RegionDto regionDtoTwo = new RegionDto();
		regionDtoTwo.setName("Kiev Oblast");
		regionDtoTwo.setCountryName("Ukraine");

		List<String> regionDtoList = new ArrayList<>();
		regionDtoList.add(regionDto.getName());
		regionDtoList.add(regionDtoTwo.getName());

		when(regionService.findAllNames()).thenReturn(regionDtoList);

		mockMvc.perform(get("/region/names"))
				.andExpect(jsonPath("$[0]").value("Masovia"))
				.andExpect(jsonPath("$[1]").value("Kiev Oblast"))
				.andExpect(status().isOk());
	}
}
