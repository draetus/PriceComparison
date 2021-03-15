package br.com.univali.pricecomparison.external.geocode.model.dto.component;

import lombok.Data;

@Data
public class GeoCodeScoring {
	
	private Double queryScore;
	private GeoCodeFieldScore fieldScore;

}
