package spatialdb.backend.exception;

import org.apache.commons.lang.exception.*;

public class CountyNotFoundException extends NestableException {

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
