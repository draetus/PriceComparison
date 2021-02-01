package br.com.univali.pricecomparison.security.model;

import lombok.Getter;

public enum RoleEnum {

	USER(4);
		 
	@Getter private final Integer id;
		
	private RoleEnum(Integer id){
		this.id = id;
	} 
}
