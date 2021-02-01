package br.com.univali.pricecomparison.api.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.user.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByCpf(String cpf);
	
}
