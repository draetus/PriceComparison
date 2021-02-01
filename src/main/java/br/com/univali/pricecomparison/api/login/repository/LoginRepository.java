package br.com.univali.pricecomparison.api.login.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.login.model.Login;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long>{

}
