package example.restlet.wrapper.server.rest.services;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import example.restlet.wrapper.server.Server.Service;

/**
 * Receives a POST request with the following JSON
 * 
 * <pre>
 * { "message" : "MY MESSAGE" }
 * </pre>
 * 
 * and answers with the same message.
 *
 * @author Pedro Domingues
 */
public final class ReflectService implements Service {
    public static final class Implementation extends AbstractServiceImplementation {

	@Post("application/json")
	public String doPost(Representation data) throws JSONException, IOException {
	    configureRestForm(this);
	    final JSONObject result = new JSONObject();
	    final JSONObject params = getParametersFromRepresentation(data);
	    final String message = params.getString("message");
	    result.put("response", "I've received: \"" + message + "\"!");
	    return result.toString();
	}
    }

    @Override
    public String getDescription() {
	return "Reflects the message received.";
    }

    @Override
    public String[] getOperations() {
	return new String[] { "POST" };
    }

    @Override
    public Object getServiceImplementation() {
	return Implementation.class;
    }

    @Override
    public String getServicePath() {
	return "/reflect";
    }
}
