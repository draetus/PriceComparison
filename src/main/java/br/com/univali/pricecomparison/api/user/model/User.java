package br.com.univali.pricecomparison.api.user.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.login.model.Login;
import br.com.univali.pricecomparison.api.user.model.DTO.UserResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName="user_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Long id;
	
	@NotNull
	private String cpf;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="id_login")	
	@NotNull
	private Login login;
	
	public User(String cpf, Login login) {
		super();
		this.cpf = cpf;
		this.login = login;
	}
	
	public UserResponse generateResponse() {
		return new UserResponse(this.cpf);
	}

}