package br.otimizes.tomicroservices.model.experimento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.otimizes.tomicroservices.base.BaseEntity;
import br.otimizes.tomicroservices.model.métricas.Metrica;
import br.otimizes.tomicroservices.model.projeto.Projeto;
import br.otimizes.tomicroservices.model.usuário.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Experimento extends BaseEntity {
	private String nome;
	private int tamanhoDaPopulação;
	private int quantidadeDeMicroservicos;
	private int quantidadeDeExecucoes;
	private int interacoesPorExecucao;
	private int probabilidadeDeMutacao;
	private int fracaoDeCruzamento;
	private int nomeDoArquivoDeEntrada;
	@ManyToOne
	@JoinColumn(name = "executado_por_id")
	private Usuario executadoPor;	
	@ManyToOne
	@JoinColumn(name = "projeto_id")
	private Projeto projeto;	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "experimento_id")
	private List<SolucaoCandidata> solucoesCandidatas = new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "experimento_id")
	private List<Metrica> metricas = new ArrayList<>();
}
