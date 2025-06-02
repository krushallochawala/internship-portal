/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package EJB;

import Entity.Companies;
import Entity.Students;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
@Stateless
public class loginBean implements loginBeanLocal {

    @PersistenceContext(unitName = "InternshipPU")
    EntityManager em;
    
    @Override
    public Companies Companylogin(String email, String password) {
        try{
            return em.createQuery("SELECT c FROM Companies c WHERE c.email = :email AND c.password = :password", Companies.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public void logout(HttpSession session) {
        if(session != null){
            session.invalidate();
        }
    }
    
    @Override
    public Students Studentlogin(String email, String password) {
        try{
            return em.createQuery("SELECT s FROM Students s WHERE s.email = :email AND s.password = :password", Students.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        catch(Exception e){
            return null;
        }
    }
}
