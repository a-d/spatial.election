package edu.spatial.election.database.exceptions;

import org.apache.commons.lang.exception.NestableException;

public class StateNotFoundException extends NestableException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StateNotFoundException(String msg) {
		super(msg);
	}
	
	public StateNotFoundException(Throwable t) {
		super(t);
	}

	public StateNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
