package br.com.univali.pricecomparison.api.productprice.repository;

import br.com.univali.pricecomparison.api.productprice.model.dto.ProductPriceResponse;

public interface ProductPriceRepositoryCustom {
	
	public ProductPriceResponse findBestPrice(String barcode, Double latUser, Double lonUser);
	
	public ProductPriceResponse findBestPriceNear(String barcode, Double latUser, Double lonUser);
	
	public Double findAveragePrice(String barcode);

}
