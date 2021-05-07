package br.com.univali.pricecomparison.api.shoppinglistproduct.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.univali.pricecomparison.api.shoppinglistproduct.model.ShoppingListProduct;
import lombok.Data;

@Data
public class ShoppingListProductResponse {
	
	private String barcode;
	
	private String name;
	
	private Long quantity;

	public ShoppingListProductResponse(String barcode, String name, Long quantity) {
		super();
		this.barcode = barcode;
		this.name = name;
		this.quantity = quantity;
	}
	
	public static List<ShoppingListProductResponse> generateList(List<ShoppingListProduct> products) {
		List<ShoppingListProductResponse> productResponses = products.stream()
				.map((ShoppingListProduct product) -> new ShoppingListProductResponse(product.getProduct().getBarCode(), product.getProduct().getName(), product.getQuantity()))
				.collect(Collectors.toList());
		return productResponses;
	}

}
