package br.com.univali.pricecomparison.external.geocode.model.dto.component;

import lombok.Data;

@Data
public class GeoCodeItem {
	
	private String title;
	private String id;
	private String resultType;
	private String localityType;
	private GeoCodeAddress address;
	private GeoCodePosition position;
	private GeoCodeMapView mapView;
	private GeoCodeScoring scoring;

}
