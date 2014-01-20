package edu.spatial.election.database.exceptions;

import org.apache.commons.lang.exception.NestableException;

public class PartyNotFoundException extends NestableException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PartyNotFoundException(String msg) {
		super(msg);
	}
	
	public PartyNotFoundException(Throwable t) {
		super(t);
	}

	public PartyNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
