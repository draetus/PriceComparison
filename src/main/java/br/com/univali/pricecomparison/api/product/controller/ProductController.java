package br.com.univali.pricecomparison.api.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.model.dto.ProductRequest;
import br.com.univali.pricecomparison.api.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> save(ProductRequest productRequest){
		productService.save(new Product(productRequest.getBarCode(), productRequest.getName()));
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
