package org.jetty.start;

import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

	private final int port;

	public static void main(String[] args) throws Exception {
		Main server = new Main();
		server.start();
	}

	public Main() {
		int port = 9191;
		try {
			Properties prop = new Properties();
			prop.load(getClass().getClassLoader().getResourceAsStream("jetty.properties"));
			port = Integer.parseInt(prop.getProperty("jetty.port"));
		} catch (Exception e) {
			System.out.println("Error while loading settings: " + e.getMessage());
			e.printStackTrace();
		} finally {
			this.port = port;
		}
	}

	private void start() {
		Server server = new Server(port);
		WebAppContext cntx = new WebAppContext();
		cntx.setServer(server);
		cntx.setContextPath("/");

		ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
		URL location = protectionDomain.getCodeSource().getLocation();
		cntx.setWar(location.toExternalForm());
		server.setHandler(cntx);

		try {
			server.start();
			System.out.println("Started on port: " + port);
			server.join();
			System.out.println("Exiting.");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			this.stop(server);
		} finally {
			this.stop(server);
		}
	}

	private void stop(Server server) {
		try {
			server.stop();
		} catch (Exception e1) {
			System.out.println("Error while shutting down from error.");
			e1.printStackTrace();
		} finally {
			server.destroy();
		}
	}
}
