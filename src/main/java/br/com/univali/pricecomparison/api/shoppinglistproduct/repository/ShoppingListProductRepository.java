package br.com.univali.pricecomparison.api.shoppinglistproduct.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.univali.pricecomparison.api.shoppinglistproduct.model.ShoppingListProduct;

public interface ShoppingListProductRepository extends CrudRepository<ShoppingListProduct, Long>{

}
