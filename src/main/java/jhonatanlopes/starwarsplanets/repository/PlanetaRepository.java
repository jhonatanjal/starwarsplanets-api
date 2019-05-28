package jhonatanlopes.starwarsplanets.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import jhonatanlopes.starwarsplanets.model.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {
	Optional<Planeta> findByNome(String nome);
}
