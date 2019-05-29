package jhonatanlopes.starwarsplanets.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jhonatanlopes.starwarsplanets.model.Planeta;
import jhonatanlopes.starwarsplanets.repository.PlanetaRepository;

@RestController
@RequestMapping("/planetas")
public class PlanetasControllers {

	@Autowired
	private PlanetaRepository repository;

	@GetMapping
	public List<Planeta> planetas() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Planeta> buscaPlaneta(@PathVariable String id) {
		Optional<Planeta> resultado = repository.findById(id);

		return resultado.map(planeta -> ResponseEntity.ok(planeta))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Planeta adicionaPlaneta(@RequestBody Planeta planeta) {
		return repository.save(planeta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Planeta> removePlaneta(@PathVariable String id) {
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
