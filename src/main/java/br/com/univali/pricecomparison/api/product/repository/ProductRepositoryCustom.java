package br.com.univali.pricecomparison.api.product.repository;

import java.util.List;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.model.dto.ProductFilter;

public interface ProductRepositoryCustom {
	
	public Product findByBarCode(ProductFilter filters);
	
	public List<Product> findAll(ProductFilter filters);

}
