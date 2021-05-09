package br.com.univali.pricecomparison.api.product.model.dto;

import br.com.univali.pricecomparison.api.productprice.model.dto.ProductPriceResponse;
import lombok.Data;

@Data
public class ProductResponse {
	
	private String barcode;
	
	private String name;
	
	private ProductPriceResponse bestPrice;
	
	private ProductPriceResponse bestPriceNear;
	
	private Double averagePrice;
	
	public ProductResponse(String barcode, String name, ProductPriceResponse bestPrice,
			ProductPriceResponse bestPriceNear, Double averagePrice) {
		super();
		this.barcode = barcode;
		this.name = name;
		this.bestPrice = bestPrice;
		this.bestPriceNear = bestPriceNear;
		this.averagePrice = averagePrice;
	}

}
