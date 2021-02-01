package br.com.univali.pricecomparison.security.exception;

public abstract class PriceComparisonException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PriceComparisonException() {
		super();
	}

	public PriceComparisonException(String message) {
		super(message);
	}

	public PriceComparisonException(String message, Throwable t) {
		super(message, t);
	}

}
