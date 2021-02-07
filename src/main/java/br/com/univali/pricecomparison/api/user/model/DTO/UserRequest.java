package br.com.univali.pricecomparison.api.user.model.DTO;

import br.com.univali.pricecomparison.api.login.model.Login;
import br.com.univali.pricecomparison.api.user.model.User;
import lombok.Data;

@Data
public class UserRequest {
	
	private String cpf;
	private String password;
	
	public User generateModel() {
		Login login = new Login(cpf, password, true);
		return new User(cpf, login);
	}

}
