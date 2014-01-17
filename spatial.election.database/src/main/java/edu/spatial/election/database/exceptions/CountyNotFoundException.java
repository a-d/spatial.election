package edu.spatial.election.database.exceptions;

import org.apache.commons.lang.exception.*;

public class CountyNotFoundException extends NestableException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountyNotFoundException(String msg) {
		super(msg);
	}
	
	public CountyNotFoundException(Throwable t) {
		super(t);
	}

	public CountyNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
