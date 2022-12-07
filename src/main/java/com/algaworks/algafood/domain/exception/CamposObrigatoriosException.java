package com.algaworks.algafood.domain.exception;

public class CamposObrigatoriosException extends RuntimeException {

	private static final long serialVersionUID = 8970459153592214473L;

	public CamposObrigatoriosException() {
		
	}
	
	public CamposObrigatoriosException(String msg) {
		super(msg);
	}
	
}
