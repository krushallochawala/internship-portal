/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package CDI;

import Utils.EmailUtil;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author KRUSHAL
 */
@Named(value = "contactCDIBean")
@SessionScoped
public class contactCDIBean implements Serializable {

    private String name;
    private String email;
    private String subject;
    private String phone;
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
//    public void sendEmail() {
//        String to = "internifyportal@gmail.com"; // Your receiving email
//        String from = "krushallochawala@gmail.com"; // Sender email (must be authorized)
//        String host = "smtp.gmail.com"; // For Gmail, or use your SMTP server
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", "587");
//
//        final String username = "internifyportal@gmail.com"; // Your Gmail
//        final String password = "qffe ggvf kdsi phaj";   // App Password, NOT your Gmail password
//
//        Session session = Session.getInstance(props,
//                new Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//            Message mimeMessage = new MimeMessage(session);
//            mimeMessage.setFrom(new InternetAddress(from));
//            mimeMessage.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(to));
//            mimeMessage.setSubject(subject);
//            mimeMessage.setText("Name: " + name +
//                    "\nEmail: " + email +
//                    "\nPhone: " + phone +
//                    "\nMessage: " + message);
//
//            Transport.send(mimeMessage);
//
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent successfully!", null));
//        } catch (MessagingException e) {
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error sending message: " + e.getMessage(), null));
//        }
//    }
    
    public void sendEmail() {
        try {
            String adminEmail = "internifyportal@gmail.com"; // Your email to receive contact messages
            String fullSubject = "Contact Us - " + subject;

            String body = "Name: " + name + "\n"
                        + "Email: " + email + "\n"
                        + "Phone: " + phone + "\n"
                        + "Message: " + message;

            EmailUtil.sendEmail(adminEmail, fullSubject, body);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent successfully!", null));

            // Reset fields after success
            name = "";
            email = "";
            subject = "";
            phone = "";
            message = "";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to send message: " + e.getMessage(), null));
        }
    }
    
}
