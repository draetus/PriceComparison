package br.com.univali.pricecomparison.external.geocode.model.dto.component;

import lombok.Data;

@Data
public class GeoCodeAddress {
	
	private String label;
	private String countryCode;
	private String countryName;
	private String stateCode;
	private String state;
	private String city;
	private String district;
	private String postalCode;

}
