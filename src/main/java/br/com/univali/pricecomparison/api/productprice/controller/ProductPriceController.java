package br.com.univali.pricecomparison.api.productprice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.api.productprice.model.dto.ProductPriceRequest;
import br.com.univali.pricecomparison.api.productprice.service.ProductPriceService;

@RestController
@RequestMapping("/price")
public class ProductPriceController {
	
	@Autowired
	private ProductPriceService productPriceService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ProductPriceRequest productPriceRequest){
		productPriceService.save(productPriceRequest.getBarcode(), productPriceRequest.getPrice(), productPriceRequest.getLatitude(), productPriceRequest.getLongitude());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
