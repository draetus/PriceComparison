package br.com.univali.pricecomparison.api.address.model;

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

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
	@SequenceGenerator(name="address_generator", sequenceName="address_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Long id;
	
	@NotNull
	private Double lat;
	
	@NotNull
	private Double lon;
	
	@NotNull
	@CreationTimestamp
	private Date createdDate;
	
	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	public Address(Double lat, Double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

}
