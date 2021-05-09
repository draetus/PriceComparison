package br.com.univali.pricecomparison.api.productprice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.productprice.model.ProductPrice;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long>, ProductPriceRepositoryCustom {

}
