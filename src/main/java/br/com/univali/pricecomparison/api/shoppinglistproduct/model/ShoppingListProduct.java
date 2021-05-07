package br.com.univali.pricecomparison.api.shoppinglistproduct.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.product.model.Product;
import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shoppinglistproduct")
@NoArgsConstructor
public class ShoppingListProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shoppinglistproduct_generator")
	@SequenceGenerator(name="shoppinglistproduct_generator", sequenceName="shoppinglistproduct_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="shoppinglist_id", nullable=false)
	private ShoppingList shoppingList;
	
	private Long quantity;

	public ShoppingListProduct(Product product, ShoppingList shoppingList, Long quantity) {
		super();
		this.product = product;
		this.shoppingList = shoppingList;
		this.quantity = quantity;
	}

}
