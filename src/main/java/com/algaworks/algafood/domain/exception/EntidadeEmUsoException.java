package com.algaworks.algafood.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = 6164727756406016422L;
	
	public EntidadeEmUsoException() {
		
	}
	
	public EntidadeEmUsoException(String msg) {
		super(msg);
	}

}
