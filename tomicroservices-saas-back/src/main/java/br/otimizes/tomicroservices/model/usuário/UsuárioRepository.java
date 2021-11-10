package br.otimizes.tomicroservices.model.usuário;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuárioRepository extends JpaRepository<Usuario, String>{

	Optional<Usuario> findByEmail(String email);

}
