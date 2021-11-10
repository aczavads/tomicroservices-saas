package br.otimizes.tomicroservices.uc.efetuarLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class GerenciadorEfetuarLogin {
	@Autowired
	private ServiceEfetuarLogin service;
	
	@PostMapping
	public void validarLogin(@RequestBody Credenciais credenciais) {
		if (!service.validarLogin(credenciais)) {
			throw new RuntimeException("Credenciais inválidas!");
		}
	}

}
