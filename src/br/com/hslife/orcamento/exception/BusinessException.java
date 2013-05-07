package br.com.hslife.orcamento.exception;

import br.com.hslife.orcamento.util.Util;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = -6615643150827475837L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Exception exception) {
		super(exception);
	}
	
	public BusinessException(String message, Exception exception) {
		super(message, exception);
	}
	
	public BusinessException(String[] messages) {
		super(Util.montarString(messages));		
	}
	
	public BusinessException(String[] messages, Exception exception) {
		super(Util.montarString(messages), exception);
	}
}