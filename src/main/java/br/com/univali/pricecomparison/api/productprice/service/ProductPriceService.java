package br.com.univali.pricecomparison.api.productprice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.address.model.Address;
import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.service.ProductService;
import br.com.univali.pricecomparison.api.productprice.model.ProductPrice;
import br.com.univali.pricecomparison.api.productprice.repository.ProductPriceRepository;

@Service
public class ProductPriceService {
	
	@Autowired
	private ProductPriceRepository productPriceRepository;
	
	@Autowired
	private ProductService productService;
	
	public ProductPrice save(String barCode, Double price, Double latitude, Double longitude) {
		Optional<Product> product = productService.findByBarCode(barCode);
		if (!product.isPresent()) {
			throw new RuntimeException("Produto ainda não registrado");
		}
		
		Address address = new Address(
				latitude,
				longitude);
		
		ProductPrice productPrice = new ProductPrice(product.get(), address, price);
		
		return productPriceRepository.save(productPrice);
	}

}
