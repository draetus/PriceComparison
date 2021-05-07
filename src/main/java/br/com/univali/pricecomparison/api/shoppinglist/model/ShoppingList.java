package br.com.univali.pricecomparison.api.shoppinglist.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.shoppinglistproduct.model.ShoppingListProduct;
import br.com.univali.pricecomparison.api.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shoppinglist")
@NoArgsConstructor
public class ShoppingList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shoppinglist_generator")
	@SequenceGenerator(name="shoppinglist_generator", sequenceName="shoppinglist_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Long id;
	
	private String name;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@NotNull
	@OneToMany
	@JoinColumn(name="shoppinglist_id")
	private List<ShoppingListProduct> shoppingListProducts;
	
	public ShoppingList(User user, String name) {
		super();
		this.user = user;
		this.name = name;
	}

}
