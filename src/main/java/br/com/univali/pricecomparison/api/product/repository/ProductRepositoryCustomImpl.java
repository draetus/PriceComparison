package br.com.univali.pricecomparison.api.product.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.product.model.dto.ProductFilter;
import br.com.univali.pricecomparison.component.RepositoryCustomImpl;

@SuppressWarnings("unchecked")
@Repository
public class ProductRepositoryCustomImpl extends RepositoryCustomImpl implements ProductRepositoryCustom {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private static final String NAME_FILTER = 
			" ( LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) ) ";
	
	private void filters(ProductFilter filter, StringBuilder sqlBuilder) {
		List<String> filters = new ArrayList<String>();
		
		if (filter.getName() != null)
			filters.add(NAME_FILTER);
		
		parseFilters(filters, sqlBuilder);
	}
	
	private void filterParameters(Query query, ProductFilter filter) {
		
		if (filter.getName() != null)
			query.setParameter("name", filter.getName());
	}

	@Override
	public List<Product> findAll(ProductFilter filters) {
		StringBuilder sqlBuilder = new StringBuilder();
		
		sqlBuilder.append(" SELECT p ");
		sqlBuilder.append(" FROM Product p ");
		filters(filters, sqlBuilder);
		
		Query query = entityManager.createQuery(sqlBuilder.toString());
		filterParameters(query, filters);
		
		List<Product> lista = query.getResultList();
		return lista;
	}

}
