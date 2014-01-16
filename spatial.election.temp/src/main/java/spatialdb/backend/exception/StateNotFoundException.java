package spatialdb.backend.exception;

import org.apache.commons.lang.exception.NestableException;

public class StateNotFoundException extends NestableException {

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
