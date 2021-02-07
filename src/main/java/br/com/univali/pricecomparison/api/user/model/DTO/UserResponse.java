package br.com.univali.pricecomparison.api.user.model.DTO;

import lombok.Data;

@Data
public class UserResponse {
	
	private String cpf;

	public UserResponse(String cpf) {
		super();
		this.cpf = cpf;
	}

}
