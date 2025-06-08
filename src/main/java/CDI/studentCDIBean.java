/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.studentClient;
import Entity.Students;
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
@Named(value = "studentCDIBean")
@SessionScoped
public class studentCDIBean implements Serializable {

    studentClient client = new studentClient();
    Students student = new Students();
    UploadedFile profileImage;
    UploadedFile resume;

    private String profileImageError;
    private String resumeError;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/jpg");
    private static final List<String> ALLOWED_RESUME_TYPES = List.of("application/pdf");

    public UploadedFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(UploadedFile profileImage) {
        this.profileImage = profileImage;
    }

    public UploadedFile getResume() {
        return resume;
    }

    public void setResume(UploadedFile resume) {
        this.resume = resume;
    }

    public String getProfileImageError() {
        return profileImageError;
    }

    public void setProfileImageError(String profileImageError) {
        this.profileImageError = profileImageError;
    }

    public String getResumeError() {
        return resumeError;
    }

    public void setResumeError(String resumeError) {
        this.resumeError = resumeError;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public String addStudent() {
        profileImageError = null;
        resumeError = null;
        boolean hasError = false;

        // Profile image validation
        if (profileImage != null && profileImage.getFileName() != null) {
            String imageContentType = profileImage.getContentType();
            long imageSize = profileImage.getSize();

            if (!ALLOWED_IMAGE_TYPES.contains(imageContentType)) {
                profileImageError = "* Only JPEG, JPG, or PNG images are allowed.";
                hasError = true;
            }

            if (imageSize > MAX_FILE_SIZE) {
                profileImageError = "* Profile image file size must be less than 5MB.";
                hasError = true;
            }

            if (!hasError) {
                try {
                    String originalFileName = profileImage.getFileName();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    String uniqueFileName = UUID.randomUUID().toString() + extension;
                    String uploadPath = "C:\\sem8_project\\Online-Internship-Portal-AdminDashboard\\src\\main\\webapp\\resources\\uploads\\studentProfiles\\";

                    File targetFolder = new File(uploadPath);
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }

                    Files.copy(profileImage.getInputStream(), new File(uploadPath, uniqueFileName).toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    student.setProfileImage(uniqueFileName);
                    System.out.println("Uploaded to: " + uploadPath + uniqueFileName);

                } catch (IOException e) {
                    profileImageError = "Error uploading profile image.";
                    hasError = true;
                    e.printStackTrace();
                }
            }
        } else {
            profileImageError = "* Profile image is required.";
            hasError = true;
        }

        // Resume validation
        if (resume != null && resume.getFileName() != null) {
            String resumeContentType = resume.getContentType();
            long resumeSize = resume.getSize();

            if (!ALLOWED_RESUME_TYPES.contains(resumeContentType)) {
                resumeError = "* Only PDF files are allowed for resumes.";
                hasError = true;
            }

            if (resumeSize > MAX_FILE_SIZE) {
                resumeError = "* Resume file size must be less than 5MB.";
                hasError = true;
            }

            if (!hasError) {
                try {
                    String originalFileName = resume.getFileName();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    String uniqueFileName = UUID.randomUUID().toString() + extension;
                    String uploadPath = "C:\\sem8_project\\Online-Internship-Portal-AdminDashboard\\src\\main\\webapp\\resources\\uploads\\studentResume\\";

                    File targetFolder = new File(uploadPath);
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }

                    Files.copy(resume.getInputStream(), new File(uploadPath, uniqueFileName).toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    student.setResume(uniqueFileName);
                    System.out.println("Uploaded to: " + uploadPath + uniqueFileName);

                } catch (IOException e) {
                    resumeError = "Error uploading resume.";
                    hasError = true;
                    e.printStackTrace();
                }
            }
        } else {
            resumeError = "* Resume is required.";
            hasError = true;
        }

        if (hasError) {
            return null;
        }
        try {
            // Save student using client
            client.addStudent(student);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student registered successfully."));
            // Reset form
            student = new Students();
            resume = null;
            profileImage = null;
            return "login.xhtml";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to register student."));
            ex.printStackTrace();
            return null;
        }
    }
}
