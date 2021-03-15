package br.com.univali.pricecomparison.api.company.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.address.model.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "company")
@NoArgsConstructor
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
	@SequenceGenerator(name="company_generator", sequenceName="company_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Long id;
	
	private String name;
	
	@NotNull
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="address_id", nullable=false)
	private Address address;
	
	public Company(String name, Double lat, Double lon) {
		super();
		this.name = name;
		this.address = new Address(lat, lon);
	}

}
