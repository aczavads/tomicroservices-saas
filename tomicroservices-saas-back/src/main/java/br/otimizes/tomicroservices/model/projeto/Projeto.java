package br.otimizes.tomicroservices.model.projeto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.otimizes.tomicroservices.base.BaseEntity;
import br.otimizes.tomicroservices.model.usuário.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Data
@Indexed
public class Projeto extends BaseEntity {

	@Field
	private String nome;

	@Field
	private String identificador;

	@ManyToOne
	@JoinColumn(name = "proprietario_id")
	private Usuario proprietario;

	@OneToMany
	@JoinColumn(name = "projeto_id")	
	private List<Usuario> participantes = new ArrayList<>();
		


}
