/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Client.companyClient;
import Client.loginClient;
import Client.roleClient;
import Client.studentClient;
import DTO.LoginRequest;
import Entity.Roles;
import Utils.JwtUtil;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ws.rs.core.GenericType;
import io.jsonwebtoken.Claims;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author DELL
 */
@Named(value = "loginCDIBean")
@SessionScoped
public class loginCDIBean implements Serializable {

    loginClient loginClient = new loginClient();
    roleClient roleClient = new roleClient();

    LoginRequest loginRequest = new LoginRequest();
    List<Roles> roles;

    String errorMessage;
    String token;
    String profileEmail;

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

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Roles> getAllRoles() {
        return roleClient.getAllRoles(new GenericType<List<Roles>>() {
        });
    }

    public String login() {
        if ("Student".equals(loginRequest.getRole())) {
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

        } else if ("Company".equals(loginRequest.getRole())) {
            try {
                Response res = loginClient.loginCompany(loginRequest);
                if (res.getStatus() == 200) {
                    String json = res.readEntity(String.class);
                    String token = json.replace("{\"token\": \"", "").replace("\"}", "");
                    this.token = token;

                    // Decode token in JSF app
                    Claims claims = JwtUtil.validateToken(token);
                    String email = claims.getSubject();
                    int companyId = claims.get("userId", Integer.class);
                    this.profileEmail = email;
                  

                    // Store in JSF session
                    HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                            .getExternalContext().getSession(true);
                    session.setAttribute("companyEmail", email);
                    session.setAttribute("companyId", companyId);
                    
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
                return null;
            }
        }
        return "index.xhtml";
    }
    
    public boolean isLoggedIn() {
        return profileEmail != null && !profileEmail.isEmpty();
    }
    
    public String logout(){
        try{
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
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
