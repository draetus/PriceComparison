package br.com.univali.pricecomparison.api.shoppinglist.model.dto;

import lombok.Data;

@Data
public class AddProductToShoppingListRequest {
	
	private String barcode;
	
	private Long quantity;

}
