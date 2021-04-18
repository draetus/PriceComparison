package br.com.univali.pricecomparison.api.product.model.dto;

import lombok.Data;

@Data
public class ProductResponse {
	
	private String barcode;
	
	private String name;

	public ProductResponse(String barCode, String name) {
		super();
		this.barcode = barCode;
		this.name = name;
	}

}
