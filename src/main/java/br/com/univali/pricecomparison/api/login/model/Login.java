package br.com.univali.pricecomparison.api.login.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sun.istack.NotNull;

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
	
	@NotNull
	@CreationTimestamp
	private Date createdDate;
	
	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	public Login(String username, String password, Boolean enable) {
		super();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.username = username;
		this.password = bCryptPasswordEncoder.encode(password);
		this.enable = enable;
	}

}
