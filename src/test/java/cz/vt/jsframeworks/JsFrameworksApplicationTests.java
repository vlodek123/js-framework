package cz.vt.jsframeworks;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.vt.jsframeworks.entity.Framework;
import cz.vt.jsframeworks.repository.FrameworkRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JsFrameworksApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	FrameworkRepository frameworkRepository;

	private Framework[] frameworks = new Framework[] {
			new Framework("Angular", "500", 10L, LocalDate.parse("2025-07-31")),
			new Framework("React", "18.2.10", 8L, LocalDate.parse("2024-07-31")),
			new Framework("React", "17.0.35", 7L, LocalDate.parse("2024-07-31")),
			new Framework("React", "18.0.40", 6L, LocalDate.parse("2024-07-31")),
			new Framework("Meteor", "2.7.89", 5L, LocalDate.parse("2024-07-31")),
			new Framework("Mithril", "2.1.100", 4L, LocalDate.parse("2024-07-31"))
	};

	@Test
	public void frameworkNotFoundTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/framework/111");
		mockMvc.perform(request).andExpect(status().isNotFound());
	}
	
	@Test
	public void getFrameworkTest() throws Exception {
		Framework framework = frameworkRepository.findById(1L).get();

		RequestBuilder request = MockMvcRequestBuilders.get("/framework/1");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.frameworkName").value(framework.getFrameworkName()))
				.andExpect(jsonPath("$.version").value(framework.getVersion()));
	}

	@DirtiesContext
	@Test
	public void getFrameworksTest() throws Exception {
		for (Framework framework : frameworks) {
			frameworkRepository.save(framework);
		}

		RequestBuilder request = MockMvcRequestBuilders.get("/framework/all");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(frameworks.length + 6))
				.andExpect(jsonPath(
						"$.[?(@.id == \"7\" && @.frameworkName == \"Angular\" && @.version == \"500\")]")
						.exists());
	}

	@DirtiesContext
	@Test
	public void saveFrameworksTest() throws Exception {
		frameworkRepository.save(frameworks[0]);

		RequestBuilder request = MockMvcRequestBuilders.get("/framework/7");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.frameworkName").value(frameworks[0].getFrameworkName()))
				.andExpect(jsonPath("$.version").value(frameworks[0].getVersion()))
				.andExpect(jsonPath("$.[?(@.id == \"7\" && @.frameworkName == \"Angular\" && @.version == \"500\")]")
						.exists());
	}

	@Test
	public void validFrameworkCreation() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/framework")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper
						.writeValueAsString(new Framework("Meteor", "2.7.101", 6L, LocalDate.parse("2050-07-31"))));

		mockMvc.perform(request).andExpect(status().isCreated());
	}

	@Test
	public void invalidFrameworkCreation() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/framework")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper
						.writeValueAsString(new Framework("React", "v1", 12L, LocalDate.parse("2055-07-31"))));

		mockMvc.perform(request).andExpect(status().isBadRequest());
	}

	@DirtiesContext
	@Test
	public void updateFrameworksTest() throws Exception {
		Framework framework = new Framework("Angular", "4", 5L, LocalDate.parse("2030-07-31"));
		framework.setId(1l);

		RequestBuilder request = MockMvcRequestBuilders.put("/framework/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper
						.writeValueAsString(framework));

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.frameworkName").value(framework.getFrameworkName()))
				.andExpect(jsonPath("$.version").value(framework.getVersion()))
				.andExpect(jsonPath("$.hypeLevel").value(framework.getHypeLevel()));
	}

	@DirtiesContext
	@Test
	public void deleteFrameworksTest() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.delete("/framework/1");

		mockMvc.perform(request).andExpect(status().isNoContent());
	}

	@DirtiesContext
	@Test
	public void updateHypeLevelTest() throws Exception {
		Framework framework = frameworkRepository.findById(1L).get();

		RequestBuilder request = MockMvcRequestBuilders.patch("/framework/hl/1")
				.param("hypelevel", "3");

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.frameworkName").value(framework.getFrameworkName()))
				.andExpect(jsonPath("$.version").value(framework.getVersion()))
				.andExpect(jsonPath("$.hypeLevel").value("3"));
	}

	@DirtiesContext
	@Test
	public void updateDeprecationDateTest() throws Exception {
		Framework framework = frameworkRepository.findById(2L).get();

		RequestBuilder request = MockMvcRequestBuilders.patch("/framework/dd/2")
				.param("deprecationdate", "2100-01-01");

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.frameworkName").value(framework.getFrameworkName()))
				.andExpect(jsonPath("$.version").value(framework.getVersion()))
				.andExpect(jsonPath("$.deprecationDate").value("2100-01-01"));
	}

	@DirtiesContext
	@Test
	public void updateHypeLevelAndDeprecationDateTest() throws Exception {
		Framework framework = frameworkRepository.findById(3L).get();

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("hypelevel", "5");
		requestParams.add("deprecationdate", "2100-01-01");

		RequestBuilder request = MockMvcRequestBuilders.patch("/framework/3")
				.params(requestParams);

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.frameworkName").value(framework.getFrameworkName()))
				.andExpect(jsonPath("$.version").value(framework.getVersion()))
				.andExpect(jsonPath("$.hypeLevel").value("5"))
				.andExpect(jsonPath("$.deprecationDate").value("2100-01-01"));
	}

	@Test
	public void getFrameworksByStringTest() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/framework/searchstring")
				.param("infix", "ea");

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(3))
				.andExpect(jsonPath(
						"$.[?(@.id == \"2\" && @.frameworkName == \"React\" && @.version == \"18.2.0\")]")
						.exists())
				.andExpect(jsonPath(
						"$.[?(@.id == \"3\" && @.frameworkName == \"React\" && @.version == \"17.0.1\")]")
						.exists())
				.andExpect(jsonPath(
						"$.[?(@.id == \"4\" && @.frameworkName == \"React\" && @.version == \"18.0.0\")]")
						.exists());
	}

	@Test
	public void getByHypeLevelGreaterThanEqualTest() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/framework/searchhlgte")
				.param("hypelevel", "5");

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(5))
				.andExpect(jsonPath(
						"$.[?(@.id == \"1\" && @.frameworkName == \"Angular\" && @.version == \"4\")]")
						.exists())
				.andExpect(jsonPath(
						"$.[?(@.id == \"2\" && @.frameworkName == \"React\" && @.version == \"18.2.0\")]")
						.exists())
				.andExpect(jsonPath(
						"$.[?(@.id == \"3\" && @.frameworkName == \"React\" && @.version == \"17.0.1\")]")
						.exists())
				.andExpect(jsonPath(
						"$.[?(@.id == \"4\" && @.frameworkName == \"React\" && @.version == \"18.0.0\")]")
						.exists())
				.andExpect(jsonPath(
						"$.[?(@.id == \"5\" && @.frameworkName == \"Meteor\" && @.version == \"2.7.3\")]")
						.exists());
	}

	@Test
	public void getByHypeLevelLessThanEqualTest() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/framework/searchhllte")
				.param("hypelevel", "5");

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath(
						"$.[?(@.id == \"5\" && @.frameworkName == \"Meteor\" && @.version == \"2.7.3\")]")
						.exists())
				.andExpect(jsonPath(
						"$.[?(@.id == \"6\" && @.frameworkName == \"Mithril\" && @.version == \"2.1.0\")]")
						.exists());
	}

}
