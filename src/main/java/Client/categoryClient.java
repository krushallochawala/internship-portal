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
 * Jersey REST client generated for REST resource:categoryREST [api]<br>
 * USAGE:
 * <pre>
 *        categoryClient client = new categoryClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author DELL
 */
public class categoryClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = Url.ClientURL;

    public categoryClient() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("api");
    }

    public <T> T getCategoryById(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("cat/{0}", new Object[]{id}));
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response updateCategory(Object requestEntity) throws ClientErrorException {
        return webTarget.path("updCat").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).put(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public <T> T getLimitedCategories(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("limitedCat");
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T getAllCategories(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("cat");
        return resource.request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public Response deleteCategory(String id) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("delCat/{0}", new Object[]{id})).request().delete(Response.class);
    }

    public Response addCategory(Object requestEntity) throws ClientErrorException {
        return webTarget.path("addCat").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).post(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public void close() {
        client.close();
    }
    
}
