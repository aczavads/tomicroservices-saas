package br.otimizes.tomicroservices.base;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@MappedSuperclass
@EqualsAndHashCode
@Getter
public class BaseEntity {
	@Id
	private String id;
	
	public BaseEntity() {
		id = UUID.randomUUID().toString();
	}
	
    
}
