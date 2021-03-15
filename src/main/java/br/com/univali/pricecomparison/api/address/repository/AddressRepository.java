package br.com.univali.pricecomparison.api.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.address.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
