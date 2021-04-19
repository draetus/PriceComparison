package br.com.univali.pricecomparison.security.context;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.univali.pricecomparison.security.model.PriceComparisonUserDetails;

public abstract class PriceComparisonSecurityContext {
	
	public static final String USER_ID = "user_id";
	public static final String ROLE_OPERATOR = "ROLE_OPERATOR";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static Long getUserId(){
		PriceComparisonUserDetails authentication = getBaseAuthentication();
		return authentication.getExtraDataValueAsLong(USER_ID);
	}
	
	public static Boolean isOperator() {
		return getBaseAuthentication().getAuthorities().stream()
				.map((GrantedAuthority role) -> role.toString())
				.collect(Collectors.toList())
				.contains(ROLE_OPERATOR);
	}
	
	public static Boolean isAdmin() {
		PriceComparisonUserDetails authentication = getBaseAuthentication(); 
		return authentication.getAuthorities().stream()
				.map((GrantedAuthority role) -> role.toString())
				.collect(Collectors.toList())
				.contains(ROLE_ADMIN);
	}
	
	private static PriceComparisonUserDetails getBaseAuthentication() {
		return (PriceComparisonUserDetails)SecurityContextHolder.getContext().getAuthentication();
	}

}
