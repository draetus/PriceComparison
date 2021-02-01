package br.com.univali.pricecomparison.api.login.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "login", schema = "public")
public class Login {
	
	@Id
	private Long id;
	
	private String username;
	
	private String password;
	
	private Boolean enable;

}
