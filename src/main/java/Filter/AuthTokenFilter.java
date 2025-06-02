/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package Filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import java.util.function.Supplier;

/**
 *
 * @author DELL
 */
public class AuthTokenFilter implements ClientRequestFilter {

    private final Supplier<String> tokenSupplier;

    public AuthTokenFilter(Supplier<String> tokenSupplier) {
        this.tokenSupplier = tokenSupplier;
    }
    
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        String token = tokenSupplier.get();
        if (token != null && !token.isEmpty()) {
            if (!requestContext.getHeaders().containsKey("Authorization")) {
                requestContext.getHeaders().add("Authorization", "Bearer " + token);
                System.out.println("Added Authorization header with token");
            }
        }
        
//         if (token == null || token.isEmpty()) {
//            System.out.println("Token is missing. Redirecting to login...");
//            throw new IOException("Unauthorized. Token is missing."); // Throw to abort the request
//        }
    }

}
