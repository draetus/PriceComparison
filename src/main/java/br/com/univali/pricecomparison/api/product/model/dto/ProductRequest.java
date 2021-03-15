package br.com.univali.pricecomparison.api.product.model.dto;

import lombok.Data;

@Data
public class ProductRequest {
	
	private String barCode;
	
	private String name;

}
