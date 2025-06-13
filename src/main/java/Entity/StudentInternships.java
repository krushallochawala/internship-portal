/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "student_internships")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentInternships.findAll", query = "SELECT s FROM StudentInternships s"),
    @NamedQuery(name = "StudentInternships.findById", query = "SELECT s FROM StudentInternships s WHERE s.id = :id"),
    @NamedQuery(name = "StudentInternships.findByEnrollDate", query = "SELECT s FROM StudentInternships s WHERE s.enrollDate = :enrollDate"),
    @NamedQuery(name = "StudentInternships.findByCompletionDate", query = "SELECT s FROM StudentInternships s WHERE s.completionDate = :completionDate"),
    @NamedQuery(name = "StudentInternships.findByStatus", query = "SELECT s FROM StudentInternships s WHERE s.status = :status")})
public class StudentInternships implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enroll_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollDate;
    @Basic(optional = false)
    @Column(name = "completion_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Students studentId;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Companies companyId;
    @JoinColumn(name = "internship_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Internships internshipId;

    public StudentInternships() {
    }

    public StudentInternships(Integer id) {
        this.id = id;
    }

    public StudentInternships(Integer id, Date enrollDate, Date completionDate, String status) {
        this.id = id;
        this.enrollDate = enrollDate;
        this.completionDate = completionDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Students getStudentId() {
        return studentId;
    }

    public void setStudentId(Students studentId) {
        this.studentId = studentId;
    }

    public Companies getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Companies companyId) {
        this.companyId = companyId;
    }

    public Internships getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Internships internshipId) {
        this.internshipId = internshipId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentInternships)) {
            return false;
        }
        StudentInternships other = (StudentInternships) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.StudentInternships[ id=" + id + " ]";
    }
    
}
