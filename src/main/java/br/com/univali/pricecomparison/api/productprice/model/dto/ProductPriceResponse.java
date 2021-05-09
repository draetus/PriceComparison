package br.com.univali.pricecomparison.api.productprice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceResponse {
	
	private Double price;
	
	private Double distance;

}
