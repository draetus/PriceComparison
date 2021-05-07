package br.com.univali.pricecomparison.api.shoppinglistproduct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.service.ProductService;
import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import br.com.univali.pricecomparison.api.shoppinglist.model.dto.AddProductToShoppingListRequest;
import br.com.univali.pricecomparison.api.shoppinglist.service.ShoppingListService;
import br.com.univali.pricecomparison.api.shoppinglistproduct.model.ShoppingListProduct;
import br.com.univali.pricecomparison.api.shoppinglistproduct.repository.ShoppingListProductRepository;
import br.com.univali.pricecomparison.security.context.PriceComparisonSecurityContext;

@Service
public class ShoppingListProductService {
	
	@Autowired
	private ShoppingListProductRepository shoppingListProductRepository;
	
	@Autowired
	private ShoppingListService shoppingListService;
	
	@Autowired
	private ProductService productService;
	
	public ShoppingListProduct addProduct(AddProductToShoppingListRequest addProductToShoppingListRequest, Long id) {
		Long userId = PriceComparisonSecurityContext.getUserId();
		ShoppingList shoppingList = shoppingListService.findByUserIdAndId(userId, id);
		Product product = productService.findByBarCode(addProductToShoppingListRequest.getBarcode());
		if (product != null || shoppingList != null) {
			ShoppingListProduct shoppingListProduct = new ShoppingListProduct(product, shoppingList, addProductToShoppingListRequest.getQuantity());
			return shoppingListProductRepository.save(shoppingListProduct);
		}
		return null;
	}
	
	public void deleteProductById(Long id, String productBarcode) {
		Long userId = PriceComparisonSecurityContext.getUserId();
		ShoppingList shoppingList = shoppingListService.findByUserIdAndId(userId, id);
		List<ShoppingListProduct> products = shoppingList.getShoppingListProducts();
		
		Integer indexToDelete = null;
		for (int i=0; i<products.size(); i++) {
			if (products.get(i).getProduct().getBarCode().equals(productBarcode)) {
				indexToDelete = i;
				break;
			}
		}
		if (indexToDelete != null) {
			shoppingListProductRepository.delete(products.get(indexToDelete.intValue()));
		}
	}

}
