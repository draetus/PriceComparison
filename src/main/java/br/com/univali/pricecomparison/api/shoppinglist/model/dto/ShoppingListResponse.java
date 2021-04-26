package br.com.univali.pricecomparison.api.shoppinglist.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import lombok.Data;

@Data
public class ShoppingListResponse {
	
	private Long id;
	
	private String name;
	
	public ShoppingListResponse(ShoppingList shoppingList) {
		this.id = shoppingList.getId();
		this.name = shoppingList.getName();
	}
	
	public static List<ShoppingListResponse> generateList(List<ShoppingList> shoppingLists) {
		List<ShoppingListResponse> shoppingListResponses = shoppingLists.stream()
				.map((ShoppingList shoppingList) -> new ShoppingListResponse(shoppingList))
				.collect(Collectors.toList());
		return shoppingListResponses;
	}

}
