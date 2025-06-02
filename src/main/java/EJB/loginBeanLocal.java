/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SessionLocal.java to edit this template
 */
package EJB;

import Entity.Companies;
import Entity.Students;
import jakarta.ejb.Local;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@Local
public interface loginBeanLocal {
    Companies Companylogin(String email,String password);
    Students Studentlogin(String email,String password);
    void logout(HttpSession session);
}
