package example.restlet.wrapper.server.rest;

import java.util.List;
import java.util.logging.Level;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

/**
 * Wrapper of a Restlet application, abstracting other classes from this implementation specific details.
 * 
 * @author Pedro Domingues (pedro.domingues@ist.utl.pt)
 */
final class RestletApplication extends Application {

    private final Component component;

    private final int serverPort;

    private final List<RestletService> services;

    /**
     * A representation of a pluggable Restlet service.
     * 
     * @author Pedro Domingues (pedro.domingues@ist.utl.pt)
     */
    public static final class RestletService {

        private final String path;

        private final Class<? extends ServerResource> service;

        /**
         * Instantiates a new restlet service.
         *
         * @param path
         *            the URI of this service
         * @param service
         *            the Restlet service implementation
         */
        public RestletService(final String path, Class<? extends ServerResource> service) {
            this.path = path;
            this.service = service;
        }

        /**
         * Gets the path of this service.
         *
         * @return a string representation of the service path
         */
        public String getPath() {
            return path;
        }

        /**
         * Gets the service implementation.
         *
         * @return the service implementation
         */
        public Class<? extends ServerResource> getService() {
            return service;
        }
    }

    /**
     * Instantiates a new Restlet application.
     *
     * @param serverPort
     *            the server port
     * @param services
     *            the services to plug in this server
     */
    public RestletApplication(final int serverPort, final List<RestletService> services) {
        this.component = new Component();
        this.serverPort = serverPort;
        this.services = services;
    }

    /**
     * Closes the server.
     *
     * @throws Exception
     */
    public void close() throws Exception {
        this.component.stop();
    }

    /**
     * Defines the routes for all incoming calls.
     * 
     * @return the routing schema
     */
    @Override
    public Restlet createInboundRoot() {
        final Router router = new Router(getContext());
        for (RestletService s : this.services) {
            router.attach(s.getPath(), s.getService());
        }
        return router;
    }

    /**
     * Starts the Restlet server, which will bing on the configured port and expose the plugged services.
     *
     * @throws Exception
     */
    public void open() throws Exception {
        Engine.setLogLevel(Level.SEVERE);
        
        this.component.getServers().add(Protocol.HTTP, this.serverPort);

        this.component.start();
        if (this.component.isStarted()) {
            this.component.getDefaultHost().attach(this);
        }
    }
}
