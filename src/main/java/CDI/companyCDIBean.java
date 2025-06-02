/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.companyClient;
import Entity.Companies;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author DELL
 */
@Named(value = "companyCDIBean")
@SessionScoped
public class companyCDIBean implements Serializable {
    companyClient client = new companyClient();
    Companies company = new Companies();
    UploadedFile logo;
    
    private String logoError;
    
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/jpg");
    private static final List<String> ALLOWED_RESUME_TYPES = List.of("application/pdf");

    public Companies getCompany() {
        return company;
    }

    public void setCompany(Companies company) {
        this.company = company;
    }

    public UploadedFile getLogo() {
        return logo;
    }

    public void setLogo(UploadedFile logo) {
        this.logo = logo;
    }

    public String getLogoError() {
        return logoError;
    }

    public void setLogoError(String logoError) {
        this.logoError = logoError;
    }
    
    public String addCompany(){
        logoError = null;
        boolean hasError = false;
        
        //Comapny logo validation
        if (logo != null && logo.getFileName() != null) {
            String imageContentType = logo.getContentType();
            long imageSize = logo.getSize();

            if (!ALLOWED_IMAGE_TYPES.contains(imageContentType)) {
                logoError = "* Only JPEG, JPG, or PNG images are allowed.";
                hasError = true;
            }

            if (imageSize > MAX_FILE_SIZE) {
                logoError = "* Logo file size must be less than 5MB.";
                hasError = true;
            }

            if (!hasError) {
                try {
                    String originalFileName = logo.getFileName();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    String uniqueFileName = UUID.randomUUID().toString() + extension;
                    String uploadPath = "E:\\Sem8-Project\\IntershipPortal-adminDashboard\\src\\main\\webapp\\resources\\uploads\\companyLogo\\";

                    File targetFolder = new File(uploadPath);
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }

                    Files.copy(logo.getInputStream(), new File(uploadPath, uniqueFileName).toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    company.setLogo(uniqueFileName);
                    System.out.println("Uploaded to: " + uploadPath + uniqueFileName);

                } catch (IOException e) {
                    logoError = "Error uploading logo.";
                    hasError = true;
                    e.printStackTrace();
                }
            }
        }
        if (hasError) {
            return null;
        }
        
        try {
            // Save student using client
            client.addCompany(company);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Company registered successfully."));
            // Reset form
            company = new Companies();
            logo = null;
            return "login.xhtml";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to register company."));
            ex.printStackTrace();
            return null;
        }
    }
}
