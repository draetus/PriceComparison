package br.com.univali.pricecomparison.api.product.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.model.dto.ProductExistsResponse;
import br.com.univali.pricecomparison.api.product.model.dto.ProductRequest;
import br.com.univali.pricecomparison.api.product.model.dto.ProductResponse;
import br.com.univali.pricecomparison.api.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ProductRequest productRequest){
		productService.save(new Product(productRequest.getBarcode(), productRequest.getName()));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/exists/{barCode}")
	public ResponseEntity<ProductExistsResponse> exists(@PathVariable String barCode){
		Boolean exists = productService.existsByBarCode(barCode);
		return new ResponseEntity<ProductExistsResponse>(new ProductExistsResponse(exists), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{barcode}")
	public ResponseEntity<ProductResponse> searchByBarCode(@PathVariable String barcode){
		Optional<Product> product = productService.findByBarCode(barcode);
		if (!product.isPresent()) {
			throw new RuntimeException("Produto não encontrado");
		}
		ProductResponse productResponse = product.get().generateResponse();
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

}
