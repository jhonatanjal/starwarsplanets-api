package jhonatanlopes.starwarsplanets.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Planeta {
	
	@Id
	private String id;
	private String nome;
	private String clima;
	private String terreno;
	private int aparicoesEmFilmes;
	
}
