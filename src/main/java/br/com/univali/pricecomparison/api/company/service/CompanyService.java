package br.com.univali.pricecomparison.api.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.company.model.Company;
import br.com.univali.pricecomparison.api.company.repository.CompanyRepository;
import br.com.univali.pricecomparison.external.geocode.model.dto.GeoCodeResponse;
import br.com.univali.pricecomparison.external.geocode.service.GeoCodeService;

@Service
public class CompanyService {
	
	private CompanyRepository companyRepository;
	
	@Autowired
	private GeoCodeService geoCodeService;
	
	public Company save(String name, String address) {
		GeoCodeResponse geoCodeResponse = geoCodeService.geoCode(address);
		
		if (geoCodeResponse.getItems().size() < 1) {
			throw new RuntimeException("Não foi possível geolocalizar o endereço");
		}
		
		Company company = new Company(
				name, 
				geoCodeResponse.getItems().get(0).getPosition().getLat(),
				geoCodeResponse.getItems().get(0).getPosition().getLng()
				);
		
		return companyRepository.save(company);
	}

}
