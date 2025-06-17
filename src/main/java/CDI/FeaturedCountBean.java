/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.applicationsClient;
import Client.companyClient;
import Client.internshipClient;
import Client.studentClient;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.core.GenericType;
import java.io.Serializable;

/**
 *
 * @author KRUSHAL
 */
@Named(value = "featuredCountBean")
@SessionScoped
public class FeaturedCountBean implements Serializable {

    long totalCompanies;
    long totalInternships;
    long totalApplications;
    long totalStudents;
    
    companyClient companyClient = new companyClient();
    studentClient studentClient = new studentClient();
    internshipClient internshipClient = new internshipClient();
    applicationsClient appClient = new applicationsClient();

    public long getTotalCompanies() {
        return totalCompanies;
    }

    public void setTotalCompanies(long totalCompanies) {
        this.totalCompanies = totalCompanies;
    }

    public long getTotalInternships() {
        return totalInternships;
    }

    public void setTotalInternships(long totalInternships) {
        this.totalInternships = totalInternships;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }
    
    @PostConstruct
    public void init() {
        totalCompanies = companyClient.getCompaniesCount(new GenericType<Long>(){});
        totalStudents = studentClient.getStudentsCount(new GenericType<Long>(){});
        totalInternships = internshipClient.getInternshipsCount(new GenericType<Long>(){});
        totalApplications = appClient.getApplicationsCount(new GenericType<Long>(){});
    }
}
