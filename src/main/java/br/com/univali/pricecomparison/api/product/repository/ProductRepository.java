package br.com.univali.pricecomparison.api.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	public Optional<Product> findByBarCode(String barCode);
	
	public Boolean existsByBarCode(String barCode);
}
