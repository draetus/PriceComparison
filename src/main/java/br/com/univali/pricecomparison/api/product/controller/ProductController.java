package br.com.univali.pricecomparison.api.product.controller;

import java.util.List;

import org.springdoc.api.annotations.ParameterObject;
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
import br.com.univali.pricecomparison.api.product.model.dto.ProductFilter;
import br.com.univali.pricecomparison.api.product.model.dto.ProductRequest;
import br.com.univali.pricecomparison.api.product.model.dto.ProductResponse;
import br.com.univali.pricecomparison.api.product.model.dto.ProductResumedResponse;
import br.com.univali.pricecomparison.api.product.service.ProductService;
import br.com.univali.pricecomparison.api.productprice.model.dto.ProductPriceResponse;
import br.com.univali.pricecomparison.api.productprice.service.ProductPriceService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPriceService productPriceService;
	
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
	
	@GetMapping(value = "/{barcode}/{lat}/{lon}")
	public ResponseEntity<ProductResponse> searchByBarCode(
			@PathVariable String barcode,
			@PathVariable Double lat,
			@PathVariable Double lon){
		Product product = productService.findByBarCode(barcode);
		if (product == null) {
			throw new RuntimeException("Produto não encontrado");
		}
		ProductPriceResponse bestPrice = productPriceService.findBestPrice(barcode, lat, lon);
		ProductPriceResponse bestPriceNear = productPriceService.findBestPriceNear(barcode, lat, lon);
		Double averagePrice = productPriceService.findAveragePrice(barcode);
		ProductResponse productResponse = new ProductResponse(product.getBarCode(), product.getName(), bestPrice, bestPriceNear, averagePrice);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}
	
	@GetMapping(value = "")
	public ResponseEntity<List<ProductResumedResponse>> findAll(@ParameterObject ProductFilter productFilter){
		List<Product> products = productService.findAll(productFilter);
		List<ProductResumedResponse> productResponses = ProductResumedResponse.generateList(products);
		return new ResponseEntity<List<ProductResumedResponse>>(productResponses, HttpStatus.OK);
	}

}
