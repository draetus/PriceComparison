package br.com.univali.pricecomparison.api.productprice.model.dto;

import lombok.Data;

@Data
public class ProductPriceRequest {
	
	private String barCode;
	
	private Double price; 
	
	private String address;

}
