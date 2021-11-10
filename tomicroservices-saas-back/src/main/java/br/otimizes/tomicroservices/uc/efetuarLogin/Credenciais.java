package br.otimizes.tomicroservices.uc.efetuarLogin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Credenciais {
	private String email;
	private String senhaCriptografada;
}