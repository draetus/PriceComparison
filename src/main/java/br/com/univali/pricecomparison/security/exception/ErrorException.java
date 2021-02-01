package br.com.univali.pricecomparison.security.exception;

public class ErrorException extends PriceComparisonException{

	private static final long serialVersionUID = 1L;

	public ErrorException(String message, Throwable e) {
		super(message, e);
	}
	
}
