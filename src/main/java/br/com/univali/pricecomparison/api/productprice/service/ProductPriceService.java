package br.com.univali.pricecomparison.api.productprice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.address.model.Address;
import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.service.ProductService;
import br.com.univali.pricecomparison.api.productprice.model.ProductPrice;
import br.com.univali.pricecomparison.api.productprice.repository.ProductPriceRepository;
import br.com.univali.pricecomparison.external.geocode.model.dto.GeoCodeResponse;
import br.com.univali.pricecomparison.external.geocode.service.GeoCodeService;

@Service
public class ProductPriceService {
	
	@Autowired
	private ProductPriceRepository productPriceRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private GeoCodeService geoCodeService;
	
	public ProductPrice save(String barCode, Double price, String address) {
		Optional<Product> product = productService.findByBarCode(barCode);
		if (!product.isPresent()) {
			throw new RuntimeException("Produto ainda não registrado");
		}
		
		GeoCodeResponse geoCodeResponse = geoCodeService.geoCode(address);
		
		if (geoCodeResponse.getItems().size() < 1) {
			throw new RuntimeException("Não foi possível geolocalizar o endereço");
		}
		
		Address a = new Address(
				geoCodeResponse.getItems().get(0).getPosition().getLat(),
				geoCodeResponse.getItems().get(0).getPosition().getLng());
		
		ProductPrice productPrice = new ProductPrice(product.get(), a, price);
		
		return productPriceRepository.save(productPrice);
	}

}
