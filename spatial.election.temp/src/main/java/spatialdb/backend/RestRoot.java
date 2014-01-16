package spatialdb.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/root")
public class RestRoot {
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces("text/plain")
	public String sayHello() {
		return "Hello Jersey";
	}
}
