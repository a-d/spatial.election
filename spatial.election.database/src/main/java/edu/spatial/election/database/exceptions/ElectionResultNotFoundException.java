package edu.spatial.election.database.exceptions;

import org.apache.commons.lang.exception.NestableException;

public class ElectionResultNotFoundException extends NestableException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElectionResultNotFoundException(String msg) {
		super(msg);
	}
	
	public ElectionResultNotFoundException(Throwable t) {
		super(t);
	}

	public ElectionResultNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
