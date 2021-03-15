package br.com.univali.pricecomparison.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.external.geocode.model.dto.GeoCodeResponse;
import br.com.univali.pricecomparison.external.geocode.service.GeoCodeService;

@RestController
@RequestMapping("/test")
public class TesteController {
	
	@Autowired
	private GeoCodeService geoCodeService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<GeoCodeResponse> save(@RequestParam(required = false) String address){
		return new ResponseEntity<GeoCodeResponse>(geoCodeService.geoCode(address), HttpStatus.OK);
	}

}
