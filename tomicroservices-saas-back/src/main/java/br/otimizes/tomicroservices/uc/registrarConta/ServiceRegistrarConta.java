package br.otimizes.tomicroservices.uc.registrarConta;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.otimizes.tomicroservices.model.usuário.Usuario;
import br.otimizes.tomicroservices.model.usuário.UsuárioRepository;

@Service
@Transactional
public class ServiceRegistrarConta {
	@Autowired
	private UsuárioRepository repo;

	public void inserir(Usuario novo) {
		Optional<Usuario> opUsuário = repo.findByEmail(novo.getEmail());
		if (opUsuário.isPresent()) {
			throw new UsuárioJáExistenteException();
		}
		repo.save(novo);
	}
	
	
}

