/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/JerseyClient.java to edit this template
 */
package Client;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:loginREST [api]<br>
 * USAGE:
 * <pre>
 *        loginClient client = new loginClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author DELL
 */
public class loginClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:55335/internship-portal/resources";

    public loginClient() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("api");
    }

    public Response logout() throws ClientErrorException {
        return webTarget.path("user/logout").request().post(null, Response.class);
    }

    public Response loginStudent(Object requestEntity) throws ClientErrorException {
        return webTarget.path("student/login").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).post(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public Response loginCompany(Object requestEntity) throws ClientErrorException {
        return webTarget.path("company/login").request(jakarta.ws.rs.core.MediaType.APPLICATION_JSON).post(jakarta.ws.rs.client.Entity.entity(requestEntity, jakarta.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public void close() {
        client.close();
    }
    
}
