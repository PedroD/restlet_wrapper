package example.restlet.wrapper.server.rest;

import java.util.LinkedList;
import java.util.List;

import org.restlet.resource.ServerResource;

import example.restlet.wrapper.server.Server;
import example.restlet.wrapper.server.rest.RestletApplication.RestletService;

/**
 * Implementation of a REST server using the Restlet library.
 * 
 * @author Pedro Domingues (pedro.domingues@ist.utl.pt)
 */
public final class RESTServer implements Server {

    private final RestletApplication restlet;
    private final List<Service> services;

    public RESTServer(final int serverPort, final List<Service> services) {
        this.services = services;

        final List<RestletService> restletServices = new LinkedList<RestletService>();
        for (Service s : this.services) {
            /**
             * This unchecked conversion cannot be avoided if we want to keep our interfaces clean from the Restlet implementation
             * details.
             * <p>
             * However if a ClassCastException is thrown, it means that the received service implementations are not compatible
             * with the Restlet implementation.
             */
            try {
                @SuppressWarnings("unchecked")
                final RestletService restService = new RestletService(s.getServicePath(),
                        (Class<? extends ServerResource>) s.getServiceImplementation());
                restletServices.add(restService);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(
                        "One or more of the received service implementations are not compatible with Restlet.");
            }
        }
        this.restlet = new RestletApplication(serverPort, restletServices);
    }

    @Override
    public void close() throws ServerOperationException {
        try {
            this.restlet.close();
        } catch (Exception e) {
            throw new ServerOperationException("A fatal error occured while closing the Restlet server.", e);
        }
    }

    @Override
    public List<Service> getServices() {
        return this.services;
    }

    @Override
    public void open() throws ServerOperationException {
        try {
            this.restlet.open();
        } catch (Exception e) {
            throw new ServerOperationException("A fatal error occured while opening the Restlet server.", e);
        }
    }

}
