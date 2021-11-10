package br.otimizes.tomicroservices.model.experimento;

import javax.persistence.Entity;

import br.otimizes.tomicroservices.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SolucaoCandidata extends BaseEntity {
	private String nomeDoArquivoDeSaida;
}
