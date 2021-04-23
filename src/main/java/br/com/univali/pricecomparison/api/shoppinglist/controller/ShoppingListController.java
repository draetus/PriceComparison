package br.com.univali.pricecomparison.api.shoppinglist.controller;

import java.util.List;

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

import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
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
	
	@GetMapping
	public ResponseEntity<List<ShoppingListResponse>> findAll(){
		List<ShoppingList> shoppingLists = shoppingListService.findAllMy();
		List<ShoppingListResponse> shoppingListResponses = ShoppingListResponse.generateList(shoppingLists);
		return new ResponseEntity<>(shoppingListResponses, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		shoppingListService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
