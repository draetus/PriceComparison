package br.com.univali.pricecomparison.api.shoppinglist.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.model.dto.ProductResponse;
import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import lombok.Data;

@Data
public class ShoppingListResponse {
	
	private Long id;
	
	private List<ProductResponse> products;
	
	public ShoppingListResponse(ShoppingList shoppingList) {
		List<Product> products = shoppingList.getProducts();
		List<ProductResponse> productResponses = ProductResponse.generateList(products);
		this.products = productResponses;
		this.id = shoppingList.getId();
	}
	
	public static List<ShoppingListResponse> generateList(List<ShoppingList> shoppingLists) {
		List<ShoppingListResponse> shoppingListResponses = shoppingLists.stream()
				.map((ShoppingList shoppingList) -> new ShoppingListResponse(shoppingList))
				.collect(Collectors.toList());
		return shoppingListResponses;
	}

}
