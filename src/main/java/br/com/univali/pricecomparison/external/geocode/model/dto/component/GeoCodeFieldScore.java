package br.com.univali.pricecomparison.external.geocode.model.dto.component;

import java.util.List;

import lombok.Data;

@Data
public class GeoCodeFieldScore {
	
	private Double country;
	private Double city;
	private List<Double> streets;
	private Double houseNumber;
	private Double postalCode;

}
