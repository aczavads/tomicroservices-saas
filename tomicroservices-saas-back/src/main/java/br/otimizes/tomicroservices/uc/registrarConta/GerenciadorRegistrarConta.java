package br.otimizes.tomicroservices.uc.registrarConta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.otimizes.tomicroservices.model.usuário.Usuario;

@RestController
@RequestMapping("/api/contas")
public class GerenciadorRegistrarConta { 
	@Autowired
	private ServiceRegistrarConta service;
	
	@PostMapping
	public String inserir(@RequestBody Usuario novo) {
		service.inserir(novo);
		return novo.getId();
	}

}
