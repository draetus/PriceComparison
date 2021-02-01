package br.com.univali.pricecomparison.api.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.login.model.Login;
import lombok.Data;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User {
	
	@Id
	private Long id;
	
	@NotNull
	private String cpf;
	
	@OneToOne
	@JoinColumn(name="id_login")	
	@NotNull
	private Login login;

}