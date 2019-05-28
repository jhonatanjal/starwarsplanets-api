package jhonatanlopes.starwarsplanets.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class Planeta {
	
	@Id
	private String id;
	@NonNull
	private String nome;
	@NonNull
	private String clima;
	@NonNull
	private String terreno;
	
	private int aparicoesEmFilmes;
	
}
