package br.com.univali.pricecomparison.component.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LatLonBoxDTO {
	
	private final Double minLat;
	private final Double maxLat;
	private final Double minLon;
	private final Double maxLon;

}
