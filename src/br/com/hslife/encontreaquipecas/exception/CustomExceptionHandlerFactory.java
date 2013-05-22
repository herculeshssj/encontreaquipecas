package br.com.hslife.encontreaquipecas.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/*
 * Tutorial: http://wmarkito.wordpress.com/2012/04/05/adding-global-exception-handling-using-jsf-2-x-exceptionhandler/
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {
	
	private ExceptionHandlerFactory parent;

	public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}
	
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler handler = new CustomExceptionHandler(parent.getExceptionHandler()); 
        return handler;
    }
}
