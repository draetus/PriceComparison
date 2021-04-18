package br.com.univali.pricecomparison.api.product.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.product.model.dto.ProductResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
public class Product {
	
	@Id
	@NotNull
	private String barCode;
	
	private String name;
	
	@NotNull
	@CreationTimestamp
	private Date createdDate;
	
	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	public Product(String barCode, String name) {
		super();
		this.barCode = barCode;
		this.name = name;
	}
	
	public ProductResponse generateResponse() {
		return new ProductResponse(barCode, name);
	}

}