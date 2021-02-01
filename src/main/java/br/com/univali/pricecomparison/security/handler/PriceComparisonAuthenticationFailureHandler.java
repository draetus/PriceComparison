package br.com.univali.pricecomparison.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.univali.pricecomparison.security.exception.ResponseLog;

public class PriceComparisonAuthenticationFailureHandler implements AuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		response.setContentType("application/json;charset=UTF-8");
		String responseMessage = null;
		ResponseEntity<ResponseLog> feedback = null;
		HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;

		responseMessage = getMessageByStatus(responseStatus);
		feedback = new ResponseEntity<>(new ResponseLog(responseMessage), responseStatus);

		ObjectMapper mapper = new ObjectMapper();
		response.getOutputStream().write(mapper.writeValueAsBytes(feedback));
		response.setStatus(responseStatus.value());

	}

	private String getMessageByStatus(HttpStatus status) {
		return status.toString();
	}

}
