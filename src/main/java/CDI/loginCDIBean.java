/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.loginClient;
import Client.studentClient;
import DTO.LoginRequest;
import Entity.Roles;
import Entity.Students;
import Utils.JwtUtil;
import Utils.EmailUtil;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ws.rs.core.GenericType;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author DELL
 */
@Named(value = "loginCDIBean")
@SessionScoped
public class loginCDIBean implements Serializable {

    loginClient loginClient = new loginClient();
    studentClient studentClient = new studentClient();
    LoginRequest loginRequest = new LoginRequest();

    String errorMessage;
    String token;
    String profileEmail;

    String newPassword;
    String confirmPassword;
    String forgotEmail;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getForgotEmail() {
        return forgotEmail;
    }

    public void setForgotEmail(String forgotEmail) {
        this.forgotEmail = forgotEmail;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    @PostConstruct
    public void init() {
        String uri = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestServletPath(); // e.g. "/resetPassword.xhtml"

        if (uri != null && uri.contains("resetPassword.xhtml")) {
            String emailParam = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap()
                    .get("email");
            if (emailParam != null) {
                this.forgotEmail = emailParam;
            }
        }
    }

    public String login() {
        System.out.println("Login");
        try {
            Response res = loginClient.loginStudent(loginRequest);
            if (res.getStatus() == 200) {
                String json = res.readEntity(String.class);
                String token = json.replace("{\"token\": \"", "").replace("\"}", "");
                this.token = token;

                // Decode token in JSF app
                Claims claims = JwtUtil.validateToken(token);
                String email = claims.getSubject();
                int studentId = claims.get("userId", Integer.class);
                this.profileEmail = email;

                // Store in JSF session
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                        .getExternalContext().getSession(true);
                session.setAttribute("studentEmail", email);
                session.setAttribute("studentId", studentId);

                // Store token via JavaScript
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("authToken", token);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Login Successfully"));

                return "index.xhtml";
            } else {
                this.errorMessage = "Invalid email or password.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email or password", null));
                return null;
            }
        } catch (Exception e) {
            this.errorMessage = "Login failed. Please try again.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed. Please try again.", null));
            System.out.println("=========Login Failed==========" + e.getMessage());
            return null;
        }
    }

    public boolean isLoggedIn() {
        return profileEmail != null && !profileEmail.isEmpty();
    }

    public String logout() {
        try {
            Response res = loginClient.logout();
            int status = res.getStatus();

            if (status == 200) {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                System.out.println("Logged Out.");
                loginRequest = new LoginRequest();
                return "/login.xhtml?faces-redirect=true";
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendResetLink() {
        try {
            GenericType<Students> type = new GenericType<Students>() {
            };
            Students student = studentClient.getStudentByEmail(type, forgotEmail);
            if (student == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No user found with this email.", null));
                return;
            }
            // Construct reset link
            String resetLink = "http://localhost:8080/online-internship-portal/resetPassword.xhtml?email=" + forgotEmail;

            // Send email
            String subject = "Reset Your Password";
            String body = "Click the link below to reset your password:\n" + resetLink;
            EmailUtil.sendEmail(forgotEmail, subject, body); // Create this utility class

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Reset link sent to your email.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong. Try again.", null));
            e.printStackTrace();
        }
    }

    public String resetPassword() {
        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", null));
            return null;
        }

        try {
            System.out.println("Forgot Email: " + forgotEmail);
            boolean updated = studentClient.updateStudentPassword(forgotEmail, newPassword);
            if (updated) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Password updated successfully.", null));
                return "login.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to update password.", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred.", null));
            return null;
        }
    }

}
