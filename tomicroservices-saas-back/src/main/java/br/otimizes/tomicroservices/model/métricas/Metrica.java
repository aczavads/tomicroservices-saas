package br.otimizes.tomicroservices.model.métricas;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import br.otimizes.tomicroservices.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Metrica extends BaseEntity {
	private String classeConcretaAlgoritmoGenético;
	
}



