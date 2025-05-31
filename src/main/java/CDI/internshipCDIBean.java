/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.categoryClient;
import Client.internshipClient;
import Client.internshipSkillsClient;
import Entity.Categories;
import Entity.InternshipSkills;
import Entity.Internships;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ws.rs.core.GenericType;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
@Named(value = "internshipCDIBean")
@SessionScoped
public class internshipCDIBean implements Serializable {

    internshipClient client = new internshipClient();
    internshipSkillsClient internshipSkillsClient = new internshipSkillsClient();
    
    Internships internship = new Internships();
    List<InternshipSkills> internshipSkills;
    List<Internships> InternshipList;
    
    categoryClient categoryClient = new categoryClient();
    Categories category = new Categories();
    int selectedCategoryId;
            
    String mode = "limited";

    public int getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(int selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public List<Internships> getInternshipList() {
        return InternshipList;
    }

    public void setInternshipList(List<Internships> InternshipList) {
        this.InternshipList = InternshipList;
    }
            
    public List<InternshipSkills> getInternshipSkills() {
        return internshipSkills;
    }

    public void setInternshipSkills(List<InternshipSkills> internshipSkills) {
        this.internshipSkills = internshipSkills;
    }

    public Internships getInternship() {
        return internship;
    }

    public void setInternship(Internships internship) {
        this.internship = internship;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public List<Internships> getAllLimitedInternships(){
        return client.getLimitedInternships(new GenericType<List<Internships>>(){});
    }
    
    public List<Internships> getAllInternships(){
        return client.getAllInternshipses(new GenericType<List<Internships>>(){});
    }
    
    public List<List<Internships>> getGroupedInternships(){
        List<Internships> source;
        
        if("all".equalsIgnoreCase(mode)){
            source = getAllInternships();
        }
        else if("byCat".equalsIgnoreCase(mode)){
            source = client.getInternshipByCategory(new GenericType<List<Internships>>(){}, String.valueOf(selectedCategoryId));
        }
        else{
            source = getAllLimitedInternships();
        }
        
        List<List<Internships>> grouped = new ArrayList<>();
        
        for(int i=0; i<source.size(); i+=2){
            int end = Math.min(i+2,source.size());
            grouped.add(source.subList(i, end));
        }
        return grouped;
    }
    
    public String getInternshipById(int id){
        internship = client.getInternshipById(new GenericType<Internships>(){},String.valueOf(id));
        
        internshipSkills = internshipSkillsClient.getInternshipSkillsByInternship(new GenericType<List<InternshipSkills>>(){}, String.valueOf(id));
        return "internshipDetail.xhtml";
    }
    
    public String getInternshipByCat(int id){
        this.selectedCategoryId = id;
        category = categoryClient.getCategoryById(new GenericType<Categories>(){}, String.valueOf(id));
        this.mode = "byCat";
        return "categoryDetails.xhtml";
    }
    
}
