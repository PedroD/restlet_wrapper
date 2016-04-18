package example.restlet.wrapper.server.rest.services;

import java.io.IOException;
import java.io.Reader;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Header;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

public abstract class AbstractServiceImplementation extends ServerResource {

    /**
     * Enables incoming connections from different servers.
     * 
     * @param serverResource
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final static Series<Header> configureRestForm(ServerResource serverResource) {
        Series<Header> responseHeaders = (Series<Header>) serverResource.getResponse().getAttributes()
                .get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            serverResource.getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add("Access-Control-Allow-Origin", "*");
        responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
        responseHeaders.add("Access-Control-Allow-Credentials", "false");
        responseHeaders.add("Access-Control-Max-Age", "60");
        return responseHeaders;
    }

    public final static JSONObject getParametersFromRepresentation(Representation e) throws JSONException, IOException {
        final Reader r = e.getReader();
        final StringBuffer sb = new StringBuffer();
        int c;
        while ((c = r.read()) != -1) {
            sb.append((char) c);
        }
        return new JSONObject(sb.toString());
    }
}
