/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/JerseyClient.java to edit this template
 */
package Client;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:studentFeedbackREST [api]<br>
 * USAGE:
 * <pre>
 *        feedbackClient client = new feedbackClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author DELL
 */
public class feedbackClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = Url.ClientURL;

    public feedbackClient() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("api");
    }

    public <T> T getAllFeedbacks(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("studFeedbacks");
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getFeedbackById(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("studFeedback/{0}", new Object[]{id}));
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getFeedbackByStudent(GenericType<T> responseType, String studentId) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("feedbackByStudent/{0}", new Object[]{studentId}));
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response addFeedback(Object requestEntity) throws ClientErrorException {
        return webTarget.path("addStudFeedback").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).post(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public Response deleteFeedback(String id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("delStudFeedback/{0}", new Object[]{id})).request().delete(Response.class);
    }

    public <T> T getFeedbackByCompany(GenericType<T> responseType, String companyId) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("feedbackByCompany/{0}", new Object[]{companyId}));
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response updateFeedback(Object requestEntity) throws ClientErrorException {
        return webTarget.path("updStudFeedback").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).put(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public void close() {
        client.close();
    }
    
}
