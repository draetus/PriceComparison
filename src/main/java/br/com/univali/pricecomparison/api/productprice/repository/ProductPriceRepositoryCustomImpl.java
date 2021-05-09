package br.com.univali.pricecomparison.api.productprice.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.productprice.model.ProductPrice;
import br.com.univali.pricecomparison.api.productprice.model.dto.ProductPriceResponse;
import br.com.univali.pricecomparison.component.GeoDistanceService;
import br.com.univali.pricecomparison.component.RepositoryCustomImpl;

@SuppressWarnings("unchecked")
@Repository
public class ProductPriceRepositoryCustomImpl extends RepositoryCustomImpl implements ProductPriceRepositoryCustom {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private GeoDistanceService geoDistanceService;
	
	private static final Double NEAR_PRICE_DISTANCE_METERS = 5000d;

	@SuppressWarnings("REFATORAR - REALIZAR CALCULO PELO BANCO")
	@Override
	public ProductPriceResponse findBestPrice(String barcode, Double latUser, Double lonUser) {
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append(" SELECT pr ");
		sqlBuilder.append(" FROM ProductPrice pr ");
		sqlBuilder.append(" WHERE pr.product.barCode = :barcode ");
		
		Query query = entityManager.createQuery(sqlBuilder.toString());
		query.setParameter("barcode", barcode);
		
		List<ProductPrice> lista = query.getResultList();
		
		Double distanceAux;
		List<Double> distances = new ArrayList<Double>();
		List<Double> priceWeights = new ArrayList<Double>();
		
		for (ProductPrice productPrice : lista) {
			distanceAux = geoDistanceService.distance(latUser, lonUser, productPrice.getAddress().getLat(), productPrice.getAddress().getLon());
			priceWeights.add(distanceAux * 0.3d + productPrice.getPrice() * 0.7);
			distances.add(distanceAux);
		}
		
		Integer selectedPriceWeightIndex = 0;
		Integer selectedDistanceIndex = 0;
		for (int i=0; i<priceWeights.size(); i++) {
			if (priceWeights.get(i) < priceWeights.get(selectedPriceWeightIndex.intValue())) {
				selectedPriceWeightIndex = i;
				selectedDistanceIndex = i;
			}
		}
		
		return new ProductPriceResponse(lista.get(selectedPriceWeightIndex).getPrice(), distances.get(selectedDistanceIndex));
	}

	@SuppressWarnings("REFATORAR - REALIZAR CALCULO PELO BANCO")
	@Override
	public ProductPriceResponse findBestPriceNear(String barcode, Double latUser, Double lonUser) {
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append(" SELECT pr ");
		sqlBuilder.append(" FROM ProductPrice pr ");
		sqlBuilder.append(" WHERE pr.product.barCode = :barcode ");
		
		Query query = entityManager.createQuery(sqlBuilder.toString());
		query.setParameter("barcode", barcode);
		
		List<ProductPrice> lista = query.getResultList();
		
		Double price = null;
		Double distanceFinal = null;
		Double distanceAux;
		for (ProductPrice productPrice : lista) {
			distanceAux = geoDistanceService.distance(latUser, lonUser, productPrice.getAddress().getLat(), productPrice.getAddress().getLon());
			if (distanceAux < NEAR_PRICE_DISTANCE_METERS && (price == null || productPrice.getPrice() < price)) {
				price = productPrice.getPrice();
				distanceFinal = distanceAux;
			}
		}
		
		return new ProductPriceResponse(price, distanceFinal);
	}

	@Override
	public Double findAveragePrice(String barcode) {
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append(" SELECT AVG(pr.price) ");
		sqlBuilder.append(" FROM ProductPrice pr ");
		sqlBuilder.append(" WHERE pr.product.barCode = :barcode ");
		
		Query query = entityManager.createQuery(sqlBuilder.toString());
		query.setParameter("barcode", barcode);
		
		List<Double> lista = query.getResultList();
		return lista.size() == 0 ? null : lista.get(0);
	}
	
	

}
