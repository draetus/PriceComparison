package br.com.univali.pricecomparison.api.shoppinglist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.api.product.model.dto.ProductResponse;
import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import br.com.univali.pricecomparison.api.shoppinglist.model.dto.AddProductToShoppingListRequest;
import br.com.univali.pricecomparison.api.shoppinglist.model.dto.CreateShoppingListRequest;
import br.com.univali.pricecomparison.api.shoppinglist.model.dto.ShoppingListResponse;
import br.com.univali.pricecomparison.api.shoppinglist.service.ShoppingListService;

@RestController
@RequestMapping("/shoppinglist")
public class ShoppingListController {
	
	@Autowired
	private ShoppingListService shoppingListService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> save(@RequestBody CreateShoppingListRequest createShoppingListRequest){
		shoppingListService.createNew(createShoppingListRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping(value = "/{id}/product")
	public ResponseEntity<?> addProduct(
			@RequestBody AddProductToShoppingListRequest createShoppingListRequest,
			@PathVariable Long id){
		shoppingListService.addProduct(createShoppingListRequest, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<ShoppingListResponse>> findAll(){
		List<ShoppingList> shoppingLists = shoppingListService.findAllMy();
		List<ShoppingListResponse> shoppingListResponses = ShoppingListResponse.generateList(shoppingLists);
		return new ResponseEntity<>(shoppingListResponses, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/products")
	public ResponseEntity<List<ProductResponse>> findProductsId(@PathVariable Long id){
		Optional<ShoppingList> shoppingList = shoppingListService.findById(id);
		if (shoppingList.isPresent()) {
			return new ResponseEntity<>(ProductResponse.generateList(shoppingList.get().getProducts()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ArrayList<ProductResponse>(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		shoppingListService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}/product/{productBarcode}")
	public ResponseEntity<?> deleteProductFromList(
			@PathVariable Long id,
			@PathVariable String productBarcode){
		shoppingListService.deleteProductById(id, productBarcode);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
