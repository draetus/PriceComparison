package br.com.univali.pricecomparison.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom{
	
	public Boolean existsByBarCode(String barCode);
}
