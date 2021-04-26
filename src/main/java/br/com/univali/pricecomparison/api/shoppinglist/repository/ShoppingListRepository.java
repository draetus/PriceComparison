package br.com.univali.pricecomparison.api.shoppinglist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>{
	
	@Query(value = "SELECT sl FROM ShoppingList sl WHERE sl.user.id = :userId")
	public List<ShoppingList> findAllByUserId(Long userId);
	
	@Query(value = "SELECT sl FROM ShoppingList sl WHERE sl.user.id = :userId AND sl.id = :id")
	public ShoppingList findByUserIdAndId(Long userId, Long id);

}
