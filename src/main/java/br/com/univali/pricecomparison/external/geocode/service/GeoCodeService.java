package br.com.univali.pricecomparison.external.geocode.service;

import java.time.Duration;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.univali.pricecomparison.external.geocode.model.dto.GeoCodeResponse;

@Service
public class GeoCodeService {
	
	private final RestTemplate restTemplate;
	
	@Value("${maps.baseurl}")
	private String baseUrl;
	
	@Value("${maps.apikey}")
	private String apiKey;
	
	private String API_KEY_NAME = "apiKey";
	
	private String ADDRESS_NAME = "q";
	
	public GeoCodeService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder
				.setConnectTimeout(Duration.ofSeconds(500))
				.setReadTimeout(Duration.ofSeconds(500))
				.build();
	}
	
	public GeoCodeResponse geoCode(String address) {
		HttpHeaders headers = new HttpHeaders(); 
        headers.setContentType(MediaType.APPLICATION_JSON); 
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
        		.queryParam(API_KEY_NAME, this.apiKey)
        		.queryParam(ADDRESS_NAME, address);
        
        ResponseEntity<GeoCodeResponse> geoCodeResponse = this.restTemplate.getForEntity(builder.toUriString(), GeoCodeResponse.class);
        
        return geoCodeResponse.getBody();
	}

}
