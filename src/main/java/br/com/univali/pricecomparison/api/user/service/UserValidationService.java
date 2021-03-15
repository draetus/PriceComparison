package br.com.univali.pricecomparison.api.user.service;

import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.user.model.DTO.UserRequest;

@Service
public class UserValidationService {
	
	public void validateRegisterRequest(UserRequest userRequest) {
		if (userRequest.getCpf() == null) { 
			throw new RuntimeException("CPF OBRIGATORIO");
		}
		if (userRequest.getPassword() == null) {
			throw new RuntimeException("PASSWORD OBRIGATORIO");
		}
	}

}
