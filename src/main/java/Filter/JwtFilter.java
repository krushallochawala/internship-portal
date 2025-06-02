/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package Filter;

import Utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Priority;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author DELL
 */
//@WebFilter(filterName = "JwtFilter", urlPatterns = {"/*"})
@Provider
@JWTRequired
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {
    @Context
    HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String authHeader = crc.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            crc.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Authorization header must be provided").build());
            return;
        }

        String token = authHeader.substring("Bearer".length()).trim();

        try {
            Claims claims = JwtUtil.validateToken(token);
            int userId = (int) claims.get("userId");
            String email = claims.getSubject();
            
            //Store claims in session
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userId);
            session.setAttribute("email", email);
            
        } catch (ExpiredJwtException e) {
            crc.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token expired").build());
        } catch (Exception e) {
            crc.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid token").build());
        }
    }

}
