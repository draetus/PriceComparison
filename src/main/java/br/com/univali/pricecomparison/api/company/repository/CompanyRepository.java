package br.com.univali.pricecomparison.api.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.company.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

}
