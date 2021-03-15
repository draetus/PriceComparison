package br.com.univali.pricecomparison.api.productprice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.univali.pricecomparison.api.address.model.Address;
import br.com.univali.pricecomparison.api.company.model.Company;
import br.com.univali.pricecomparison.api.product.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product_price")
@NoArgsConstructor
public class ProductPrice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_price_generator")
	@SequenceGenerator(name="product_price_generator", sequenceName="product_price_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", nullable=false)
	private Product product;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="address_id", nullable=false)
	private Address company;
	
	private Double price;

	public ProductPrice(Product product, Address company, Double price) {
		super();
		this.product = product;
		this.company = company;
		this.price = price;
	}

}
