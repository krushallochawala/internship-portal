/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.applicationsClient;
import Client.bookmarkClient;
import Client.educationClient;
import Client.experienceClient;
import Client.feedbackClient;
import Client.internshipClient;
import Client.paymentClient;
import Client.studentClient;
import Entity.Applications;
import Entity.Bookmarks;
import Entity.Education;
import Entity.Internships;
import Entity.Payments;
import Entity.StudentFeedback;
import Entity.Students;
import Entity.WorkExperience;
import Enum.EnumClass;
import Enum.EnumClass.Gender;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.resource.spi.work.WorkException;
import jakarta.ws.rs.core.GenericType;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
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
    applicationsClient appClient = new applicationsClient();
    bookmarkClient bookmarkClient = new bookmarkClient();
    internshipClient internshipClient = new internshipClient();
    educationClient eduClient = new educationClient();
    experienceClient expClient = new experienceClient();
    feedbackClient feedbackClient = new feedbackClient();
    paymentClient paymentClient = new paymentClient();

    Students student = new Students();
    Applications application = new Applications();
    Bookmarks bookmark = new Bookmarks();
    Education education = new Education();
    WorkExperience exp = new WorkExperience();
    StudentFeedback feedback = new StudentFeedback();
    Payments payment = new Payments();

    Integer internshipId;
    
    UploadedFile profileImage;
    UploadedFile resume;

    private String profileImageError;
    private String resumeError;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/jpg");
    private static final List<String> ALLOWED_RESUME_TYPES = List.of("application/pdf");

    long totalApplications;
    long selectedCount;
    long rejectedCount;
    long pendingCount;

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

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(long selectedCount) {
        this.selectedCount = selectedCount;
    }

    public long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(long pendingCount) {
        this.pendingCount = pendingCount;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public WorkExperience getExp() {
        return exp;
    }

    public void setExp(WorkExperience exp) {
        this.exp = exp;
    }

    public StudentFeedback getFeedback() {
        return feedback;
    }

    public void setFeedback(StudentFeedback feedback) {
        this.feedback = feedback;
    }

    public Integer getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Integer internshipId) {
        this.internshipId = internshipId;
    }

    public Applications getApplication() {
        return application;
    }

    public void setApplication(Applications application) {
        this.application = application;
    }

    public Payments getPayment() {
        return payment;
    }

    public void setPayment(Payments payment) {
        this.payment = payment;
    }

    @PostConstruct
    public void init() {
        try {
            Integer studentId = (Integer) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("studentId");
            totalApplications = appClient.getApplicationCountByStudent(new GenericType<Long>() {
            }, String.valueOf(studentId));
            selectedCount = appClient.getSelectedCountByStudent(new GenericType<Long>() {
            }, String.valueOf(studentId));
            rejectedCount = appClient.getRejectedCountByStudent(new GenericType<Long>() {
            }, String.valueOf(studentId));
            pendingCount = appClient.getPendingCountByStudent(new GenericType<Long>() {
            }, String.valueOf(studentId));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error : " + e.getMessage());
        }
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
                    String uploadPath = ImageUrl.studentProfile;

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
                    String uploadPath = ImageUrl.studentResume;

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

    public String goToDashboard() {
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        student = client.getStudentbyId(new GenericType<Students>() {
        }, String.valueOf(studentId));
        return "/student/myDashboard.xhtml";
    }

    public int getReviewedPercentage() {
        int total = (int) (selectedCount + rejectedCount + pendingCount);
        int reviewed = (int) (selectedCount + rejectedCount);
        return total > 0 ? (reviewed * 100 / total) : 0;
    }

    public String gotoInternshipApply() {
        return "/student/applyStatus.xhtml";
    }
    
    public List<Applications> getMyApplications(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        return appClient.getApplicationByStudent(new GenericType<List<Applications>>() {
        }, String.valueOf(studentId));
    }

    public String saveInternship(int id) {
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        if (studentId == null) {
            return "login.xhtml";
        } else {
            Students stud = client.getStudentbyId(new GenericType<Students>() {
            }, String.valueOf(studentId));
            Internships internship = internshipClient.getInternshipById(new GenericType<Internships>() {
            }, String.valueOf(id));
            bookmark.setStudentId(stud);
            bookmark.setInternshipId(internship);
            bookmarkClient.addBookMark(bookmark);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Internship Saved."));
            return null;
        }
    }
    
    public String goToBookMarks(){
        return "/student/myBookmarks.xhtml";
    }
    
    public List<Bookmarks> getMyBookMarks(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        return bookmarkClient.getBookMarksByStudent(new GenericType<List<Bookmarks>>(){}, String.valueOf(studentId));
    }
    
    public String delBookMark(int id){
        bookmarkClient.deleteBookMark(String.valueOf(id));
        return null;
    }
    
    public String goToMyProfile(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        student = client.getStudentbyId(new GenericType<Students>() {
        }, String.valueOf(studentId));
        return "/student/myProfile.xhtml";
    }
     
    public Gender[] getGenders() {
        return Gender.values();
    }
    
    public List<Education> getMyEducationList(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        return eduClient.getEducationByStudent(new GenericType<List<Education>>(){}, String.valueOf(studentId));
    }
    
    public List<WorkExperience> getMyExperienceList(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        return expClient.getExperienceByStudent(new GenericType<List<WorkExperience>>(){}, String.valueOf(studentId));
    }
    
    public String updateStudent() {
        profileImageError = null;
        resumeError = null;

        boolean profileHasError = false;
        boolean resumeHasError = false;

        // Path to the folder where profiles and resume are stored
        String profilePath = ImageUrl.studentProfile;
        String resumePath = ImageUrl.studentResume;
        // Profile image validation
        if (profileImage != null && profileImage.getFileName() != null) {
            String imageContentType = profileImage.getContentType();
            long imageSize = profileImage.getSize();

            if (!ALLOWED_IMAGE_TYPES.contains(imageContentType)) {
                profileImageError = "* Only JPEG, JPG, or PNG images are allowed.";
                profileHasError = true;
            }

            if (imageSize > MAX_FILE_SIZE) {
                profileImageError = "* Profile image file size must be less than 5MB.";
                profileHasError = true;
            }

            if (!profileHasError) {
                try {
                    // Remove old profile file if it exists
                    String oldProfileFileName = student.getProfileImage(); // This is the currently stored logo name
                    if (oldProfileFileName != null && !oldProfileFileName.isEmpty()) {
                        File oldFile = new File(profilePath + oldProfileFileName);
                        if (oldFile.exists()) {
                            boolean deleted = oldFile.delete();
                            System.out.println("Old file deleted: " + deleted);
                        }
                    }

                    //Save new file
                    String originalFileName = profileImage.getFileName();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    String uniqueFileName = UUID.randomUUID().toString() + extension;

                    File targetFolder = new File(profilePath);
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }

                    Files.copy(profileImage.getInputStream(), new File(profilePath, uniqueFileName).toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    student.setProfileImage(uniqueFileName);
                    System.out.println("Uploaded to: " + profilePath + uniqueFileName);

                } catch (IOException e) {
                    profileImageError = "Error uploading profile image.";
                    profileHasError = true;
                    e.printStackTrace();
                }
            }
        }

        // Resume validation
        if (resume != null && resume.getFileName() != null) {
            String resumeContentType = resume.getContentType();
            long resumeSize = resume.getSize();

            if (!ALLOWED_RESUME_TYPES.contains(resumeContentType)) {
                resumeError = "* Only PDF files are allowed for resumes.";
                resumeHasError = true;
            }

            if (resumeSize > MAX_FILE_SIZE) {
                resumeError = "* Resume file size must be less than 5MB.";
                resumeHasError = true;
            }

            if (!resumeHasError) {
                try {
                    // Remove old resume file if it exists
                    String oldResumeFileName = student.getResume(); // This is the currently stored logo name
                    if (oldResumeFileName != null && !oldResumeFileName.isEmpty()) {
                        File oldFile = new File(resumePath + oldResumeFileName);
                        if (oldFile.exists()) {
                            boolean deleted = oldFile.delete();
                            System.out.println("Old file deleted: " + deleted);
                        }
                    }

                    //Save new file
                    String originalFileName = resume.getFileName();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    String uniqueFileName = UUID.randomUUID().toString() + extension;

                    File targetFolder = new File(resumePath);
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }

                    Files.copy(resume.getInputStream(), new File(resumePath, uniqueFileName).toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    student.setResume(uniqueFileName);
                    System.out.println("Uploaded to: " + resumePath + uniqueFileName);

                } catch (IOException e) {
                    resumeError = "Error uploading resume.";
                    resumeHasError = true;
                    e.printStackTrace();
                }
            }
        }

        if (profileHasError || resumeHasError) {
            return null;
        }
        client.updateStudent(student);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Student updated successfully"));
        return null;
    }
    
    public String updateEducation(Education edu){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        Students stud = client.getStudentbyId(new GenericType<Students>(){},String.valueOf(studentId));
        edu.setStudentId(stud);
        eduClient.updateEducation(edu);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Education Details updated successfully"));
        return null;
    }
    
    public String addEducation(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        Students stud = client.getStudentbyId(new GenericType<Students>(){},String.valueOf(studentId));
        education.setStudentId(stud);
        eduClient.addEducation(education);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Education Details added successfully"));
        
        education = new Education();
        return null;
    }
    
    public String deleteEducation(int id){
        eduClient.deleteEducation(String.valueOf(id));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Education Details deleted successfully"));
        return null;
    }
    
    public String updateExperience(WorkExperience exp) {
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        Students stud = client.getStudentbyId(new GenericType<Students>() {
        }, String.valueOf(studentId));
        exp.setStudentId(stud);
        expClient.updateExperience(exp);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Experience updated successfully"));
        return null;
    }
    
    public String addExperience() {
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        Students stud = client.getStudentbyId(new GenericType<Students>() {
        }, String.valueOf(studentId));
        exp.setStudentId(stud);
        expClient.addExperience(exp);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Experience added successfully"));
        
        exp = new WorkExperience();
        return null;
    }
    
    public String deleteExperience(int id) {
        expClient.deleteExperience(String.valueOf(id));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Experience deleted successfully"));
        return null;
    }
    
    public String gotoMyFeedbacks(){
        return "/student/myFeedbacks.xhtml";
    }
    
    public List<StudentFeedback> getFeedbacksByStudent(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        return feedbackClient.getFeedbackByStudent(new GenericType<List<StudentFeedback>>(){}, String.valueOf(studentId));
    }
    
    public String deleteFeedback(int id){
        feedbackClient.deleteFeedback(String.valueOf(id));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success","Experience deleted successfully"));
        return null;
    }
    
    public String addFeedback(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        Students stud = client.getStudentbyId(new GenericType<Students>() {
        }, String.valueOf(studentId));
        Internships internship = internshipClient.getInternshipById(new GenericType<Internships>(){}, String.valueOf(internshipId));
        feedback.setStudentId(stud);
        feedback.setInternshipId(internship);
        feedbackClient.addFeedback(feedback);
        
        feedback = null;
        internshipId = null;
        return "/student/myFeedbacks.xhtml";
    }
    
    public String getFeedbackById(int id){
        feedback = feedbackClient.getFeedbackById(new GenericType<StudentFeedback>(){}, String.valueOf(id));
        return "editFeedback.xhtml";
    }
    
    public String editFeedback(){
        feedback.setCreatedAt(new Date());
        feedbackClient.updateFeedback(feedback);
        return "myFeedbacks.xhtml";
    }
    
    public boolean isLoggedIn(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        return studentId != null;
    }
    
    public String applyInternship(int id){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        Students stud = client.getStudentbyId(new GenericType<Students>() {
        }, String.valueOf(studentId));
        Internships internship = internshipClient.getInternshipById(new GenericType<Internships>(){}, String.valueOf(id));
        application.setStudentId(stud);
        application.setInternshipId(internship);
        application.setStatus("Pending");
        application.setIsPaymentDone(false);
        appClient.addApplication(application);
        return "/student/applyStatus.xhtml";
    }
    
    public String handlePaidInternship(){
        Integer studentId = (Integer) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("studentId");
        Students stud = client.getStudentbyId(new GenericType<Students>() {
        }, String.valueOf(studentId));
        Internships internship = internshipClient.getInternshipById(new GenericType<Internships>(){}, String.valueOf(internshipId));
        if (studentId == null) {
            return "login.xhtml?faces-redirect=true";
        }
         
        application = new Applications();
        application.setInternshipId(internship);
        application.setStudentId(stud);
        application.setStatus("Pending");
        application.setIsPaymentDone(true);
        application.setIsPaymentDone(true);
        appClient.addApplication(application);
        
        System.out.println("Application: " + application);
        
        payment = new Payments();
        payment.setApplicationId(application);
        payment.setInternshipId(internship);
        payment.setStudentId(stud);
        payment.setPaymentStatus("Success");
        payment.setPaymentMethod("RazorPay");
        
        paymentClient.addPayment(payment);
        return "/student/applyStatus.xhtml";
    }
}
    
