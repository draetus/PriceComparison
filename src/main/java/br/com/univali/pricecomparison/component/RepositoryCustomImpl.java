package br.com.univali.pricecomparison.component;

import java.util.List;

public abstract class RepositoryCustomImpl {
	
	protected void parseFilters(List<String> filters, StringBuilder sqlBuilder) {
		
		if (filters.size() > 0) {
			sqlBuilder.append(" WHERE ");
		}
		
		for (int i=0; i<filters.size(); i++) {
			if (i > 0)
				sqlBuilder.append(" AND ");
			sqlBuilder.append(filters.get(i));
		}
	}

}
