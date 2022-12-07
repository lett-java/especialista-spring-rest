package com.algaworks.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1121040781122377512L;

	public EntidadeNaoEncontradaException() {
		
	}
	
	public EntidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	
}
