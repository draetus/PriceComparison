package br.com.univali.pricecomparison.api.shoppinglist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.service.ProductService;
import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import br.com.univali.pricecomparison.api.shoppinglist.model.dto.AddProductToShoppingListRequest;
import br.com.univali.pricecomparison.api.shoppinglist.model.dto.CreateShoppingListRequest;
import br.com.univali.pricecomparison.api.shoppinglist.repository.ShoppingListRepository;
import br.com.univali.pricecomparison.api.user.model.User;
import br.com.univali.pricecomparison.api.user.service.UserService;
import br.com.univali.pricecomparison.security.context.PriceComparisonSecurityContext;

@Service
public class ShoppingListService {
	
	@Autowired
	private ShoppingListRepository shoppingListRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	public ShoppingList createNew(CreateShoppingListRequest createShoppingListRequest) {
		Long userId = PriceComparisonSecurityContext.getUserId();
		Optional<User> user = userService.findById(userId);
		ShoppingList shoppingList = new ShoppingList(user.get(), createShoppingListRequest.getName());
		return shoppingListRepository.save(shoppingList);
	}
	
	public ShoppingList addProduct(AddProductToShoppingListRequest addProductToShoppingListRequest, Long id) {
		Long userId = PriceComparisonSecurityContext.getUserId();
		ShoppingList shoppingList = shoppingListRepository.findByUserIdAndId(userId, id);
		Optional<Product> product = productService.findByBarCode(addProductToShoppingListRequest.getBarcode());
		if (product.isPresent()) {
			shoppingList.getProducts().add(product.get());
		}
		return shoppingListRepository.save(shoppingList);
	}
	
	public List<ShoppingList> findAllMy() {
		Long userId = PriceComparisonSecurityContext.getUserId();
		return shoppingListRepository.findAllByUserId(userId);
	}
	
	public void deleteById(Long id) {
		shoppingListRepository.deleteById(id);
	}
	
	public ShoppingList deleteProductById(Long id, String productBarcode) {
		Long userId = PriceComparisonSecurityContext.getUserId();
		ShoppingList shoppingList = shoppingListRepository.findByUserIdAndId(userId, id);
		List<Product> products = shoppingList.getProducts();
		
		Integer indexToDelete = null;
		for (int i=0; i<products.size(); i++) {
			if (products.get(i).getBarCode().equals(productBarcode)) {
				indexToDelete = i;
				break;
			}
		}
		if (indexToDelete != null) {
			products.remove(indexToDelete.intValue());
		}
		shoppingList.setProducts(products);
		return shoppingListRepository.save(shoppingList);
	}
	
	public Optional<ShoppingList> findById(Long id) {
		return shoppingListRepository.findById(id);
	}

}
