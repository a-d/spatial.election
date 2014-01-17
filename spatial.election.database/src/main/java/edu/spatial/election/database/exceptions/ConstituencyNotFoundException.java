package edu.spatial.election.database.exceptions;

import org.apache.commons.lang.exception.NestableException;

public class ConstituencyNotFoundException extends NestableException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConstituencyNotFoundException(String msg) {
		super(msg);
	}
	
	public ConstituencyNotFoundException(Throwable t) {
		super(t);
	}

	public ConstituencyNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
