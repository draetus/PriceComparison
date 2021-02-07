package br.com.univali.pricecomparison.api.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.security.model.AccountCredentials;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> login(@RequestBody AccountCredentials accountCredentials){
		// ROTA APENAS PARA VISUALIZAR NO OPEN API
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
