package br.com.univali.pricecomparison.api.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.pricecomparison.api.company.model.dto.CompanyRequest;
import br.com.univali.pricecomparison.api.company.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> save(CompanyRequest companyRequest){
		companyService.save(companyRequest.getName(), companyRequest.getAddress());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
