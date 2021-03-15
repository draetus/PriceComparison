package br.com.univali.pricecomparison.api.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.address.model.Address;
import br.com.univali.pricecomparison.api.address.repository.AddressRepository;
import br.com.univali.pricecomparison.external.geocode.model.dto.GeoCodeResponse;
import br.com.univali.pricecomparison.external.geocode.service.GeoCodeService;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private GeoCodeService geoCodeService;
	
	public Address save(String address) {
		GeoCodeResponse geoCodeRespone = geoCodeService.geoCode(address);
		
		Address a = new Address(
				geoCodeRespone.getItems().get(0).getPosition().getLat(),
				geoCodeRespone.getItems().get(0).getPosition().getLng()
				);
		
		return addressRepository.save(a);
	}

}
