package br.com.univali.pricecomparison.api.product.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

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

	public Product(String barCode, String name) {
		super();
		this.barCode = barCode;
		this.name = name;
	}

}