package br.com.univali.pricecomparison.api.product.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.univali.pricecomparison.api.product.model.Product;
import lombok.Data;

@Data
public class ProductResumedResponse {
	
	private String barcode;
	
	private String name;
	
	public ProductResumedResponse(String barcode, String name) {
		this.barcode = barcode;
		this.name = name;
	}
	
	public static List<ProductResumedResponse> generateList(List<Product> products) {
		List<ProductResumedResponse> productResponses = products.stream()
				.map((Product product) -> new ProductResumedResponse(product.getBarCode(), product.getName()))
				.collect(Collectors.toList());
		return productResponses;
	}

}
