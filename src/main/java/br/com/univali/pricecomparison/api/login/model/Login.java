package br.com.univali.pricecomparison.api.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "login")
@NoArgsConstructor
public class Login {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_generator")
	@SequenceGenerator(name="login_generator", sequenceName="login_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Long id;
	
	private String username;
	
	private String password;
	
	private Boolean enable;

	public Login(String username, String password, Boolean enable) {
		super();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.username = username;
		this.password = bCryptPasswordEncoder.encode(password);
		this.enable = enable;
	}

}
