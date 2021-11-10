package br.otimizes.tomicroservices.model.usuário;

import javax.persistence.Entity;

import br.otimizes.tomicroservices.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Usuario extends BaseEntity {
	private String email;
	private String senhaCriptografada;
	private String nome;		

}
