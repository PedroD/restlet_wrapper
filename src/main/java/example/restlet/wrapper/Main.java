package example.restlet.wrapper;

import java.util.LinkedList;
import java.util.List;

import example.restlet.wrapper.server.Server;
import example.restlet.wrapper.server.Server.Service;
import example.restlet.wrapper.server.rest.RESTServer;
import example.restlet.wrapper.server.rest.services.HelloWorldService;
import example.restlet.wrapper.server.rest.services.ReflectService;

public final class Main {

    private static final int PORT = 9192;

    public static void main(String[] args) throws Exception {
	final List<Service> services = new LinkedList<Service>();
	services.add(new HelloWorldService());
	services.add(new ReflectService());

	final Server server = new RESTServer(PORT, services);
	server.open();

	System.out.println("Server running with the following services:");
	for (Service s : server.getServices()) {
	    System.out.println("\t" + s.getOperations()[0] + " https://localhost:" + PORT + s.getServicePath());
	    System.out.println("\t\t" + s.getDescription());
	}

	/*
	 * Wait 120 seconds before shutdown.
	 */
	Thread.sleep(120000);

	server.close();

	System.out.println("Server closed!");
    }

}
