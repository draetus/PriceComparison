package br.com.univali.pricecomparison.api.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.model.dto.ProductFilter;
import br.com.univali.pricecomparison.api.product.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public Boolean existsByBarCode(String barCode) {
		return productRepository.existsByBarCode(barCode);
	}
	
	public Optional<Product> findByBarCode(String barCode) {
		return productRepository.findByBarCode(barCode);
	}
	
	public List<Product> findAll(ProductFilter filters) {
		return productRepository.findAll(filters);
	}

}
