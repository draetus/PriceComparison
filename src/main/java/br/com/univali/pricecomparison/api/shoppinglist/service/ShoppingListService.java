package br.com.univali.pricecomparison.api.shoppinglist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import br.com.univali.pricecomparison.api.shoppinglist.model.dto.CreateShoppingListRequest;
import br.com.univali.pricecomparison.api.shoppinglist.repository.ShoppingListRepository;
import br.com.univali.pricecomparison.api.shoppinglistproduct.model.ShoppingListProduct;
import br.com.univali.pricecomparison.api.shoppinglistproduct.repository.ShoppingListProductRepository;
import br.com.univali.pricecomparison.api.user.model.User;
import br.com.univali.pricecomparison.api.user.service.UserService;
import br.com.univali.pricecomparison.security.context.PriceComparisonSecurityContext;

@Service
public class ShoppingListService {
	
	@Autowired
	private ShoppingListRepository shoppingListRepository;
	
	@Autowired
	private ShoppingListProductRepository shoppingListProductRepository;
	
	@Autowired
	private UserService userService;
	
	public ShoppingList createNew(CreateShoppingListRequest createShoppingListRequest) {
		Long userId = PriceComparisonSecurityContext.getUserId();
		Optional<User> user = userService.findById(userId);
		ShoppingList shoppingList = new ShoppingList(user.get(), createShoppingListRequest.getName());
		return shoppingListRepository.save(shoppingList);
	}
	
	public ShoppingList findByUserIdAndId(Long userId, Long id) {
		return shoppingListRepository.findByUserIdAndId(userId, id);
	}
	
	public List<ShoppingList> findAllMy() {
		Long userId = PriceComparisonSecurityContext.getUserId();
		return shoppingListRepository.findAllByUserId(userId);
	}
	
	public void deleteById(Long id) {
		Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
		if (shoppingList.isPresent()) {
			List<ShoppingListProduct> shoppingListProducts = shoppingList.get().getShoppingListProducts();
			shoppingListProductRepository.deleteAll(shoppingListProducts);
			shoppingListRepository.deleteById(id);
		}
	}
	
	public Optional<ShoppingList> findById(Long id) {
		return shoppingListRepository.findById(id);
	}

}
