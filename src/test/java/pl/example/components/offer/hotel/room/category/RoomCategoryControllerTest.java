package pl.example.components.offer.hotel.room.category;

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

@WebMvcTest(RoomCategoryController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
class RoomCategoryControllerTest {

	@Autowired
	private RoomCategoryController roomCategoryController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoomCategoryService roomCategoryService;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private JwtUtil jwtUtil;

	private RoomCategoryDto roomCategoryDto;

	@BeforeEach
	void setUp() throws Exception {
		roomCategoryDto = new RoomCategoryDto();
		roomCategoryDto.setName("Exclusive");
	}

	@Test
	void findAllTest() throws Exception {
		RoomCategoryDto roomCategoryDtoTwo = new RoomCategoryDto();
		roomCategoryDtoTwo.setName("Standard");

		List<RoomCategoryDto> roomCategoryDtoList = new ArrayList<>();
		roomCategoryDtoList.add(roomCategoryDto);
		roomCategoryDtoList.add(roomCategoryDtoTwo);

		when(roomCategoryService.findAll()).thenReturn(roomCategoryDtoList);

		mockMvc.perform(get("/admin/hotels/rooms/categories"))
				.andExpect(jsonPath("$[0].name").value("Exclusive"))
				.andExpect(jsonPath("$[1].name").value("Standard"))
				.andExpect(status().isOk());
	}

	@Test
	void saveTest() throws Exception {
		when(roomCategoryService.save(any(RoomCategoryDto.class))).thenReturn(roomCategoryDto);

		mockMvc.perform(post("/admin/hotels/rooms/categories")
				.content(new ObjectMapper().writeValueAsString(roomCategoryDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Exclusive"))
				.andExpect(status().isCreated());
	}

	@Test
	void findByIdTest() throws Exception {
		roomCategoryDto.setId(12L);
		Optional<RoomCategoryDto> roomCategoryDtoOpt = Optional.of(roomCategoryDto);
		when(roomCategoryService.findById(anyLong())).thenReturn(roomCategoryDtoOpt);

		mockMvc.perform(get("/admin/hotels/rooms/categories/12"))
				.andExpect(jsonPath("$.id").value("12"))
				.andExpect(jsonPath("$.name").value("Exclusive"))
				.andExpect(status().isOk());
	}

	@Test
	void updateTest() throws Exception {
		roomCategoryDto.setId(12L);
		when(roomCategoryService.update(any(RoomCategoryDto.class))).thenReturn(roomCategoryDto);

		mockMvc.perform(put("/admin/hotels/rooms/categories/12")
				.content(new ObjectMapper().writeValueAsString(roomCategoryDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("12"))
				.andExpect(jsonPath("$.name").value("Exclusive"))
				.andExpect(status().isOk());
	}

	@Test
	void deleteTest() throws Exception {
		mockMvc.perform(delete("/admin/hotels/rooms/categories/12"))
				.andExpect(status().isOk());
	}
}
