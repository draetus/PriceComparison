package br.com.univali.pricecomparison.security.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.univali.pricecomparison.security.model.AccountCredentials;
import br.com.univali.pricecomparison.security.model.PriceComparisonUserDetails;
import br.com.univali.pricecomparison.security.service.TokenAuthenticationService;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	public JWTLoginFilter(String url, AuthenticationManager authManager, AuthenticationFailureHandler failureHandler) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		setAuthenticationFailureHandler(failureHandler);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(),AccountCredentials.class);
		
		Hashtable<String, Object> extraData = new Hashtable<String, Object>();
		
		return getAuthenticationManager().authenticate(new PriceComparisonUserDetails(
				credentials.getUsername(), credentials.getPassword(), Collections.emptyList(), extraData));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(response, auth, true);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		if(failed instanceof DisabledException) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Conta desativada. Entre em contato para renovação.");

		}
		if(failed instanceof BadCredentialsException) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Sua conta ou senha está incorreta. Se você não se lembra de sua senha, clique em 'Esqueci minha senha'"); 
		}
		super.unsuccessfulAuthentication(request, response, failed);
	}

}
