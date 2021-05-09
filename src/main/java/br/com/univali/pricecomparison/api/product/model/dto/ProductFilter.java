package br.com.univali.pricecomparison.api.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductFilter {
	
	private String name;
	
	private String barcode;

}
