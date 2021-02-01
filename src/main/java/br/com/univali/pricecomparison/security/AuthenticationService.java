package br.com.univali.pricecomparison.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.univali.pricecomparison.api.user.model.User;
import br.com.univali.pricecomparison.api.user.repository.UserRepository;
import br.com.univali.pricecomparison.security.model.PriceComparisonUserDetails;
import br.com.univali.pricecomparison.security.service.TokenAuthenticationService;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public PriceComparisonUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByCpf(username);
		Map<String, Object> extraData = new HashMap<String, Object>();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		extraData.put(TokenAuthenticationService.USER_ID, user.getId());
		
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		PriceComparisonUserDetails priceComparisonUserDetails = new PriceComparisonUserDetails(user.getCpf(), user.getLogin().getPassword(), authorities,
				extraData);
		return priceComparisonUserDetails;
	}

}
