package br.otimizes.tomicroservices.model.experimento;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.otimizes.tomicroservices.base.BaseEntity;
import br.otimizes.tomicroservices.model.usuário.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SolucaoFavorita extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "favoritada_id")
	private SolucaoCandidata favoritada;
	
	@ManyToOne
	@JoinColumn(name = "favoritada_por_id")
	private Usuario favoritadaPor;

}
