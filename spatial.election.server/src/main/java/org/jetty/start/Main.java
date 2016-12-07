package org.jetty.start;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import edu.spatial.election.backend.RestConstituency;
import edu.spatial.election.backend.RestCounty;
import edu.spatial.election.backend.RestParty;
import edu.spatial.election.backend.RestRoot;

public class Main {

	private final int port;
	private final String contextPath;
	private final String workPath;

	public static void main(String[] args) throws Exception {
		Main server = new Main();

		if (args.length != 1) {
			server.start();
		} else if ("stop".equals(args[0])) {
			server.stop();
		} else if ("start".equals(args[0])) {
			server.start();
		} else {
			server.usage();
		}
	}

	public Main() {
		try {
			String configFile = System.getProperty("config", "jetty.properties");
			System.getProperties().load(new FileInputStream(configFile));
		} catch (Exception ignored) {
		}

		port = Integer.parseInt(System.getProperty("jetty.port", "9191"));
		contextPath = System.getProperty("jetty.contextPath", "/");
		workPath = System.getProperty("jetty.workDir", null);

	}

	private void start() {
	
	    Server server = new Server(port);

	     
	        // Set some timeout options to make debugging easier.
	    	// SocketConnector connector = new SocketConnector();
	        //connector.setMaxIdleTime(1000 * 60 * 60);
	        //connector.setSoLingerTime(-1);
	        //connector.setPort(8080);
	        //server.setConnectors(new Connector[]{connector});
	     
	    	System.out.println("Adding WebAppContext");
	        WebAppContext cntx = new WebAppContext();
	        cntx.setServer(server);
	        cntx.setContextPath("/");

	    	System.out.println("Port: "+port);
	    	
	        ProtectionDomain protectionDomain = edu.spatial.election.backend.App.class.getProtectionDomain();
	        URL location = protectionDomain.getCodeSource().getLocation();
	        System.out.println(location.toExternalForm());
	        cntx.setWar(location.toExternalForm());
	        
	        System.out.println("Handler set.");
	        server.setHandler(cntx);
	        /*
	        try {
	            server.start();
	            System.out.println("Started");
	            System.in.read();
	            server.stop();
	            server.join();
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(100);
	        }
	        */
	        
	        
	
	    try {
	        server.start();
            System.out.println("Started");
	        server.join();
            System.out.println("Joined");
	    } catch (Exception e) {
	    	System.out.println("Error while running.");
			e.printStackTrace();
			
			
	        try {
				server.stop();
			} catch (Exception e1) {
				System.out.println("Error while shutting down from error.");
				e1.printStackTrace();
			}
		} finally {
			System.out.println("Done.");
			try {
				server.stop();
			} catch (Exception e) {
				System.out.println("Error while shutting down after completion.");
				e.printStackTrace();
			}
	        server.destroy();
	    }
	}

	private void stop() {

	}

	private void usage() {
		System.out.println("Usage: java -jar <file.jar> [start|stop|\n\t" + "start    Start the server (default)\n\t"
				+ "stop     Stop the server gracefully\n\t");
		System.exit(-1);
	}

}
