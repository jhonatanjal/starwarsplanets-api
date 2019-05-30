package jhonatanlopes.starwarsplanets.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import jhonatanlopes.starwarsplanets.model.Planeta;
import jhonatanlopes.starwarsplanets.repository.PlanetaRepository;

@RestController
@RequestMapping("/planetas")
public class PlanetasController {

	@Autowired
	private PlanetaRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public List<Planeta> planetas() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Planeta> buscaPlanetaPeloId(@PathVariable String id) {
		Optional<Planeta> resultado = repository.findById(id);

		return resultado.map(planeta -> ResponseEntity.ok(planeta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(params = { "nome" })
	public ResponseEntity<Planeta> buscaPlanetaPeloNome(@RequestParam String nome) {
		Optional<Planeta> resultado = repository.findByNome(nome);

		return resultado.map(planeta -> ResponseEntity.ok(planeta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Planeta adicionaPlaneta(@RequestBody Planeta planeta) {
		int aparicoesEmFilmes = buscaQtdDeAparicoesEmFilmes(planeta.getNome());
		planeta.setAparicoesEmFilmes(aparicoesEmFilmes);
		return repository.save(planeta);
	}

	private int buscaQtdDeAparicoesEmFilmes(String planeta) {
		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
		String urlDaBusca = "https://swapi.co/api/planets/?search=" + planeta;

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("User-Agent", userAgent);
		HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

		JsonNode json = this.restTemplate.exchange(urlDaBusca, HttpMethod.GET,
				httpEntity, JsonNode.class).getBody();

		if (json.get("results").size() == 0) {
			return 0;
		}

		return json.get("results").get(0).get("films").size();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Planeta> removePlaneta(@PathVariable String id) {
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
