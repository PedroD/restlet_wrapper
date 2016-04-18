package example.restlet.wrapper.server;

import java.util.List;

/**
 * Interface for a server capable of providing one or more addressable services.
 * <p>
 * A server object is comprised of:
 * <ul>
 * <li>An open method to start the server</li>
 * <li>A close method to safely shutdown the server freeing all connection related resources</li>
 * <li>A list of provided services</li>
 * </ul>
 * 
 * @author Pedro Domingues (pedro.domingues@ist.utl.pt)
 */
public interface Server {

    /**
     * The class <code>ServerOperationException</code> indicates potential failure conditions that a reasonable application might
     * want to catch.
     * 
     * @author Pedro Domingues (pedro.domingues@ist.utl.pt)
     */
    @SuppressWarnings("serial")
    class ServerOperationException extends Exception {

        /**
         * Instantiates a new server operation exception.
         *
         * @param message
         *            the message of this exception
         * @param cause
         *            the cause of this exception
         */
        public ServerOperationException(final String message, final Exception cause) {
            super(message, cause);
        }
    }

    /**
     * This is the interface of a service which can be provided by the server.
     * <p>
     * A service object pairs a service implementation with its given URI relative to the server's exposed API.
     * 
     * @author Pedro Domingues (pedro.domingues@ist.utl.pt)
     */
    public interface Service {

        /**
         * @return text describing this service
         */
        String getDescription();

        /**
         * @return the list of operations which can be performed (eg. if HTTP: GET, PUT, POST)
         */
        String[] getOperations();

        /**
         * @return an {@link Object} containing implementation specific details for this service, able to be processed by its
         *         respective server implementation
         */
        Object getServiceImplementation();

        /**
         * @return an URI identifying the service within the server's exposed API
         */
        String getServicePath();
    }

    /**
     * Shuts down the server, safely releasing any resources dedicated to it.
     * 
     * @throws ServerOperationException
     *             if an error occurs while closing, releasing resources or terminating some pending operation
     */
    void close() throws ServerOperationException;

    /**
     * @return the list of services provided by this server
     */
    List<Service> getServices();

    /**
     * Starts the server, allocating any resources necessary for its correct operation (network ports, disk files, etc.).
     * 
     * @throws ServerOperationException
     *             if an error occurs while opening, starting a connection, acquiring resources or other bootstrap operation
     */
    void open() throws ServerOperationException;
}
