package br.com.univali.pricecomparison.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import br.com.univali.pricecomparison.security.exception.AuthException;
import br.com.univali.pricecomparison.security.service.TokenAuthenticationService;


public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			if (authentication != null) {
				TokenAuthenticationService.addAuthentication((HttpServletResponse) response, authentication, false);
			} 
			filterChain.doFilter(request, response);
		} catch(AuthException e) {
			setUnauthorizedResponse((HttpServletResponse) response, e.getMessage());
		}
	}
	
	private void setUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
		response.sendError(HttpStatus.UNAUTHORIZED.value(), message);
	}

}