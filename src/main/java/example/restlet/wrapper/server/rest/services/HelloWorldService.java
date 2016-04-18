package example.restlet.wrapper.server.rest.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.Get;

import example.restlet.wrapper.server.Server.Service;

/**
 * Receives a GET request and answers with a Hello World!
 *
 * @author Pedro Domingues 
 */
public final class HelloWorldService implements Service {
    public static final class Implementation extends AbstractServiceImplementation {

	@Get("application/json")
	public String doGet() throws JSONException {
	    configureRestForm(this);

	    final JSONObject result = new JSONObject();
	    result.put("response", "Hello World!");
	    return result.toString();
	}
    }

    @Override
    public String getDescription() {
	return "Request a Hello World!";
    }

    @Override
    public String[] getOperations() {
	return new String[] { "GET" };
    }

    @Override
    public Object getServiceImplementation() {
	return Implementation.class;
    }

    @Override
    public String getServicePath() {
	return "/hello";
    }
}
