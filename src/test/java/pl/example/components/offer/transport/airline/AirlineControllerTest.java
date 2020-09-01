package pl.example.components.offer.transport.airline;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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
import pl.example.components.security.jwt.util.JwtUtil;

@WebMvcTest(AirlineController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
class AirlineControllerTest {

	@Autowired
	AirlineController airlineController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AirlineService airlineService;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private JwtUtil jwtUtil;
	
	AirlineDto airlineDto;

	@BeforeEach
	void setUp() throws Exception {
		airlineDto = new AirlineDto();
		airlineDto.setAirlineName("Airline Flight");
		airlineDto.setDetails("Best Airline");
	}

	@Test
	void findAllTest() throws Exception {
		AirlineDto airlineDtoNumberTwo = new AirlineDto();
		airlineDtoNumberTwo.setAirlineName("Flight Developer Airline");
		airlineDtoNumberTwo.setDetails("Best IT Airline");

		List<AirlineDto> airlineDtoList = new ArrayList<>();
		airlineDtoList.add(airlineDto);
		airlineDtoList.add(airlineDtoNumberTwo);

		when(airlineService.findAll()).thenReturn(airlineDtoList);

		mockMvc.perform(get("/admin/airlines"))
				.andDo(print())
				.andExpect(jsonPath("$[0].airlineName").value("Airline Flight"))
				.andExpect(jsonPath("$[0].details").value("Best Airline"))
				.andExpect(jsonPath("$[1].airlineName").value("Flight Developer Airline"))
				.andExpect(jsonPath("$[1].details").value("Best IT Airline"))
				.andExpect(status().isOk());
	}

	@Test
	void saveTest() throws Exception {
		when(airlineService.save(any(AirlineDto.class))).thenReturn(airlineDto);

		mockMvc.perform(post("/admin/airlines")
				.content(new ObjectMapper().writeValueAsString(airlineDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.airlineName").value("Airline Flight"))
				.andExpect(jsonPath("$.details").value("Best Airline"))
				.andExpect(status().isCreated());
	}

	@Test
	void findByIdTest() throws Exception {
		airlineDto.setId(12L);
		Optional<AirlineDto> airlineDtoOpt = Optional.of(airlineDto);
		when(airlineService.findById(anyLong())).thenReturn(airlineDtoOpt);

		mockMvc.perform(get("/admin/airlines/12"))
				.andExpect(jsonPath("$.id").value("12"))
				.andExpect(jsonPath("$.airlineName").value("Airline Flight"))
				.andExpect(jsonPath("$.details").value("Best Airline"))
				.andExpect(status().isOk());
	}

	@Test
	void updateTest() throws Exception {
		airlineDto.setId(12L);
		when(airlineService.update(any(AirlineDto.class))).thenReturn(airlineDto);

		mockMvc.perform(put("/admin/airlines/12")
				.content(new ObjectMapper().writeValueAsString(airlineDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("12"))
				.andExpect(jsonPath("$.airlineName").value("Airline Flight"))
				.andExpect(jsonPath("$.details").value("Best Airline"))
				.andExpect(status().isOk());
	}

	@Test
	void deleteTest() throws Exception {
		mockMvc.perform(delete("/admin/airlines/12"))
				.andExpect(status().isOk());
	}
}
