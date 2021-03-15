package br.com.univali.pricecomparison.external.geocode.model.dto;

import java.util.List;

import br.com.univali.pricecomparison.external.geocode.model.dto.component.GeoCodeItem;
import lombok.Data;

@Data
public class GeoCodeResponse {
	
	List<GeoCodeItem> items;

}
