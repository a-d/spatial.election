package spatialdb.backend.exception;

import org.apache.commons.lang.exception.NestableException;

public class ConstituencyNotFoundException extends NestableException {

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
