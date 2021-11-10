package br.otimizes.tomicroservices.uc.efetuarLogin;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.otimizes.tomicroservices.model.usuário.Usuario;
import br.otimizes.tomicroservices.model.usuário.UsuárioRepository;

@Service
@Transactional
public class ServiceEfetuarLogin {
	@Autowired
	private UsuárioRepository repo;

	public boolean validarLogin(Credenciais credenciais) {
		Optional<Usuario> opUsuário = repo.findByEmail(credenciais.getEmail());
		if (!opUsuário.isPresent()) {
			return false;
		}
		return opUsuário.get().getSenhaCriptografada().equals(credenciais.getSenhaCriptografada());
	}
	
	
}

