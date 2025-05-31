/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.categoryClient;
import Entity.Categories;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ws.rs.core.GenericType;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author DELL
 */
@Named(value = "categoryCDIBean")
@SessionScoped
public class categoryCDIBean implements Serializable {

    categoryClient client = new categoryClient();
    String mode = "limited";

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public List<Categories> getAllLimitedCategories(){
        return client.getLimitedCategories(new GenericType<List<Categories>>(){});
    }
    
    public List<Categories> getAllCategories(){
        return client.getAllCategories(new GenericType<List<Categories>>(){});
    }
    
    public List<Categories> getCategories(){
        List<Categories> source;
        
        if("all".equalsIgnoreCase(mode)){
            source = getAllCategories();
        }else{
            source = getAllLimitedCategories();
        }
        return source;
    }
    
}
