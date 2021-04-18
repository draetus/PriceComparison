package br.com.univali.pricecomparison.api.product.model.dto;

import lombok.Data;

@Data
public class ProductExistsResponse {
	
	private Boolean exists;

	public ProductExistsResponse(Boolean exists) {
		super();
		this.exists = exists;
	}

}
