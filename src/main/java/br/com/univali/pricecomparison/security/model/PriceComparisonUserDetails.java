package br.com.univali.pricecomparison.security.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PriceComparisonUserDetails extends UsernamePasswordAuthenticationToken implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> extraData;
	
	public PriceComparisonUserDetails(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities,  Map<String, Object> extraData) {
		super(principal, credentials, authorities);
		this.extraData = extraData;		 
	}

	@Override
	public String getPassword() {
		return (String)getCredentials();
	}

	@Override
	public String getUsername() {
		return (String)getPrincipal();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

