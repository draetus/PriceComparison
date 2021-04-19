package br.com.univali.pricecomparison.api.product.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.univali.pricecomparison.api.product.model.Product;
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
	
	public static List<ProductResponse> generateList(List<Product> products) {
		List<ProductResponse> productResponses = products.stream()
				.map((Product product) -> new ProductResponse(product.getBarCode(), product.getName()))
				.collect(Collectors.toList());
		return productResponses;
	}

}
