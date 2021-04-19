package br.com.univali.pricecomparison.api.product.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import br.com.univali.pricecomparison.api.shoppinglist.model.ShoppingList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
public class Product {
	
	@Id
	@NotNull
	private String barCode;
	
	private String name;
	
	@ManyToMany
	@JoinTable(
	  name = "shoppinglist_product", 
	  joinColumns = @JoinColumn(name = "shoppinglist_id"), 
	  inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<ShoppingList> shoppingLists;
	
	@NotNull
	@CreationTimestamp
	private Date createdDate;
	
	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
	}

	public Product(String barCode, String name) {
		super();
		this.barCode = barCode;
		this.name = name;
	}

}