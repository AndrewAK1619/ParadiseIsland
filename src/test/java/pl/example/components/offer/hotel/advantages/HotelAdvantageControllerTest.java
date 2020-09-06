package pl.example.components.offer.hotel.advantages;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

@WebMvcTest(HotelAdvantageController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
class HotelAdvantageControllerTest {

	@Autowired
	private HotelAdvantageController hotelAdvantageController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	HotelAdvantageService hotelAdvantageService;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private JwtUtil jwtUtil;

	private HotelAdvantageDto hotelAdvantageDto;

	@BeforeEach
	void setUp() throws Exception {
		hotelAdvantageDto = new HotelAdvantageDto();
		hotelAdvantageDto.setDescriptionAdvantage("Example Description");
		hotelAdvantageDto.setHotelId(22L);
	}

	@Test
	void findAllByHotelIdTest() throws Exception {
		HotelAdvantageDto hotelAdvantageDtoTwo = new HotelAdvantageDto();
		hotelAdvantageDtoTwo.setDescriptionAdvantage("Example Description - Second");
		hotelAdvantageDtoTwo.setHotelId(22L);

		List<HotelAdvantageDto> hotelAdvantageDtoTwoList = new ArrayList<>();
		hotelAdvantageDtoTwoList.add(hotelAdvantageDto);
		hotelAdvantageDtoTwoList.add(hotelAdvantageDtoTwo);

		when(hotelAdvantageService.findAllByHotelId(anyLong())).thenReturn(hotelAdvantageDtoTwoList);

		mockMvc.perform(get("/admin/hotels/22/advantages"))
				.andExpect(jsonPath("$[0].descriptionAdvantage").value("Example Description"))
				.andExpect(jsonPath("$[0].hotelId").value(22L))
				.andExpect(jsonPath("$[1].descriptionAdvantage").value("Example Description - Second"))
				.andExpect(jsonPath("$[1].hotelId").value(22L))
				.andExpect(status().isOk());
	}

	@Test
	void findByIdTest() throws Exception {
		hotelAdvantageDto.setId(12L);
		Optional<HotelAdvantageDto> hotelAdvantageDtoOpt = Optional.of(hotelAdvantageDto);
		when(hotelAdvantageService.findById(anyLong())).thenReturn(hotelAdvantageDtoOpt);

		mockMvc.perform(get("/admin/hotels/22/advantages/12"))
				.andExpect(jsonPath("$.id").value("12"))
				.andExpect(jsonPath("$.descriptionAdvantage").value("Example Description"))
				.andExpect(jsonPath("$.hotelId").value(22L))
				.andExpect(status().isOk());
	}

	@Test
	void saveTest() throws Exception {
		when(hotelAdvantageService.save(any(HotelAdvantageDto.class))).thenReturn(hotelAdvantageDto);

		mockMvc.perform(post("/admin/hotels/advantages")
				.content(new ObjectMapper().writeValueAsString(hotelAdvantageDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descriptionAdvantage").value("Example Description"))
				.andExpect(jsonPath("$.hotelId").value(22L))
				.andExpect(status().isCreated());
	}

	@Test
	void updateTest() throws Exception {
		hotelAdvantageDto.setId(12L);
		when(hotelAdvantageService.update(any(HotelAdvantageDto.class))).thenReturn(hotelAdvantageDto);

		mockMvc.perform(put("/admin/hotels/advantages/12")
				.content(new ObjectMapper().writeValueAsString(hotelAdvantageDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("12"))
				.andExpect(jsonPath("$.descriptionAdvantage").value("Example Description"))
				.andExpect(jsonPath("$.hotelId").value(22L))
				.andExpect(status().isOk());
	}

	@Test
	void deleteTest() throws Exception {
		mockMvc.perform(delete("/admin/hotels/advantages/12"))
				.andExpect(status().isOk());
	}
}
