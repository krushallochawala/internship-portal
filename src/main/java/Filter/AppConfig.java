/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Filter;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author DELL
 */
@ApplicationPath("/api")
public class AppConfig extends Application{
    
    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> resources = new HashSet<>();
        
        // Register your REST resource classes
        resources.add(REST.loginREST.class);
        
        // Register your JWT filter
        resources.add(Filter.JwtFilter.class);
        
        return resources;
    }
}
