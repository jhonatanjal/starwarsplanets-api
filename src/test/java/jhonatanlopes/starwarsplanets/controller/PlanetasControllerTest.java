package jhonatanlopes.starwarsplanets.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jhonatanlopes.starwarsplanets.model.Planeta;
import jhonatanlopes.starwarsplanets.repository.PlanetaRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PlanetasControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlanetaRepository repository;

	@MockBean
	private RestTemplate restTemplate;

	private Planeta planeta = new Planeta("554312", "Tatooine", "árido", "deserto", 5);
	private String planetaJson;

	@Before
	public void setup() throws Exception {
		this.planetaJson = new ObjectMapper().writeValueAsString(this.planeta);
	}

	@Test
	public void planetasDeveRetornarUmaListaDePlanetas() throws Exception {
		List<Planeta> planetas = Arrays.asList(this.planeta);
		when(repository.findAll()).thenReturn(planetas);
		String json = new ObjectMapper().writeValueAsString(planetas);

		this.mockMvc.perform(get("/planetas"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(json));
	}

	@Test
	public void buscaPlanetaPeloIdDeveRetornarPlanetaQuandoRecebeIdValido() throws Exception {
		String planetaId = this.planeta.getId();

		when(this.repository.findById(planetaId))
				.thenReturn(Optional.of(this.planeta));

		this.mockMvc.perform(get("/planetas/" + planetaId))
				.andExpect(status().isOk())
				.andExpect(content().json(this.planetaJson));
	}

	@Test
	public void buscaPlanetaPeloIdDeveRetornar404QuandoRecebeIdInvalido() throws Exception {
		when(this.repository.findById(this.planeta.getId()))
				.thenReturn(Optional.of(this.planeta));

		this.mockMvc.perform(get("/planetas/56434"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void buscaPlanetaPeloNomeDeveRetornarPlanetaQuandoRecebeNomeValido() throws Exception {
		String planetaNome = this.planeta.getNome();

		when(this.repository.findByNome(planetaNome))
				.thenReturn(Optional.of(this.planeta));

		this.mockMvc.perform(get("/planetas?nome=" + planetaNome))
				.andExpect(status().isOk())
				.andExpect(content().json(this.planetaJson));
	}

	@Test
	public void adicionaPlanetaDeveAdicionarPlanetaNoBanco() throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream is = loader.getResourceAsStream("swapi-planet.json");
		JsonNode jsonNode = new ObjectMapper().readValue(is, JsonNode.class);

		when(this.restTemplate.exchange(anyString(), eq(HttpMethod.GET),
				any(HttpEntity.class), eq(JsonNode.class))).thenReturn(ResponseEntity.ok(jsonNode));

		when(repository.save(new Planeta(null, "Tatooine", "árido", "deserto", 5)))
				.thenReturn(this.planeta);

		String jsonRequest = "{\"nome\":\"Tatooine\",\"clima\":\"árido\",\"terreno\":\"deserto\"}";
		this.mockMvc.perform(post("/planetas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().json(this.planetaJson));
	}

	@Test
	public void removePlanetaDeveRemoverPlanetaRequisitado() throws Exception {
		String planetaId = this.planeta.getId();

		this.mockMvc.perform(delete("/planetas/" + planetaId))
				.andExpect(status().isOk());

		verify(this.repository).deleteById(planetaId);
	}

}
