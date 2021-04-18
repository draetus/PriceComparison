package br.com.univali.pricecomparison.api.productprice.model.dto;

import lombok.Data;

@Data
public class ProductPriceRequest {
	
	private String barcode;
	
	private Double price; 
	
	private Double latitude;
	
	private Double longitude;

}
