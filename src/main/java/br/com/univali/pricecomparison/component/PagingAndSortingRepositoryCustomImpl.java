package br.com.univali.pricecomparison.component;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

@SuppressWarnings("unchecked")
public abstract class PagingAndSortingRepositoryCustomImpl<T> extends RepositoryCustomImpl {
	
	protected Page<T> pagedQuery(Pageable pageable, Query query, Query countQuery) {
		List<T> objects = (List<T>) query.getResultList();
		Long totalObjectCount = (Long)countQuery.getSingleResult();
		
		Page<T> page = new PageImpl<T>(objects, pageable, totalObjectCount);
		
		return page;
		
	}
	
	protected void addPagination(Pageable pageable, StringBuilder sqlBuilder) {
		pageParam(sqlBuilder, pageable);
		sizeParam(sqlBuilder, pageable);
		orderParam(sqlBuilder, pageable);
	}
	
	private void pageParam(StringBuilder sqlBuilder, Pageable pageable) {
		sqlBuilder.append(" OFFSET ");
		sqlBuilder.append(pageable.getPageNumber() * pageable.getPageSize());
	}
	
	private void sizeParam(StringBuilder sqlBuilder, Pageable pageable) {
		sqlBuilder.append(" LIMIT ");
		sqlBuilder.append(pageable.getPageSize());
	}
	
	private void orderParam(StringBuilder sqlBuilder, Pageable pageable) {
		if (pageable.getSort().isSorted()) {
			sqlBuilder.append(" ORDER BY ");
		}
		
		Boolean isFirst = true;
		for (Order sort : pageable.getSort().get().map((Order order) -> order).collect(Collectors.toList())) {
			if (isFirst) {
				isFirst = false;
			} else {
				sqlBuilder.append(", ");	
			}
			sqlBuilder.append("\"");
			sqlBuilder.append(sort.getProperty());
			sqlBuilder.append("\"");
			sqlBuilder.append(" ");
			sqlBuilder.append(sort.isAscending() ? "asc" : "desc");
		}
	}

}
