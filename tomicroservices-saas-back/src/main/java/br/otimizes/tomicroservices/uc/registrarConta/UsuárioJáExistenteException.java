package br.otimizes.tomicroservices.uc.registrarConta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsuárioJáExistenteException extends RuntimeException {

}
