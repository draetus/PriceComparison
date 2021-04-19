package br.com.univali.pricecomparison.api.shoppinglist.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shoppinglist")
@NoArgsConstructor
public class ShoppingList {
	
	@Id
	@NotNull
	private Long id;
	
	private String name;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@ManyToMany
	@JoinTable(
	  name = "shoppinglist_product", 
	  joinColumns = @JoinColumn(name = "product_id"), 
	  inverseJoinColumns = @JoinColumn(name = "shoppinglist_id"))
	private List<Product> products;
	
	public ShoppingList(User user, String name) {
		super();
		this.user = user;
		this.name = name;
	}

}
