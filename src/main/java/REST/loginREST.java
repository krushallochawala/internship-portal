/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package REST;

import DTO.LoginRequest;
import EJB.loginBeanLocal;
import Entity.Companies;
import Entity.Students;
import Utils.JwtUtil;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author DELL
 */
@Path("api")
@RequestScoped
public class loginREST {

    @EJB loginBeanLocal loginBean;
    
    @POST
    @Path("company/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginCompany(LoginRequest loginRequest) {
        Companies company = loginBean.Companylogin(loginRequest.email, loginRequest.password);
        if (company != null) {
            String token = JwtUtil.generateToken(company.getEmail(),company.getId());
            return Response.ok("{\"token\": \"" + token + "\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Invalid email or password\"}")
                    .build();
        }
    }
    
    @POST
    @Path("user/logout")
    public Response logout(@Context HttpServletRequest request){
        HttpSession session = request.getSession(false);
        loginBean.logout(session);
        return Response.ok("Logged out successfully").build();
    }
    
    @POST
    @Path("student/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginStudent(LoginRequest loginRequest) {
        Students student = loginBean.Studentlogin(loginRequest.email, loginRequest.password);
        if (student != null) {
            String token = JwtUtil.generateToken(student.getEmail(),student.getId());
            return Response.ok("{\"token\": \"" + token + "\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Invalid email or password\"}")
                    .build();
        }
    }
}
