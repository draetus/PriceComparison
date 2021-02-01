package br.com.univali.pricecomparison.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Acesso negado")
public class AuthException extends PriceComparisonException{

	private static final long serialVersionUID = 1L;

	public AuthException() {
		super();
	}

	public AuthException(String message) {
		super(message);
	}

}

