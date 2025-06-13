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
import jakarta.persistence.Lob;
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
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "work_experience")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkExperience.findAll", query = "SELECT w FROM WorkExperience w"),
    @NamedQuery(name = "WorkExperience.findById", query = "SELECT w FROM WorkExperience w WHERE w.id = :id"),
    @NamedQuery(name = "WorkExperience.findByCompanyName", query = "SELECT w FROM WorkExperience w WHERE w.companyName = :companyName"),
    @NamedQuery(name = "WorkExperience.findByRole", query = "SELECT w FROM WorkExperience w WHERE w.role = :role"),
    @NamedQuery(name = "WorkExperience.findByStartDate", query = "SELECT w FROM WorkExperience w WHERE w.startDate = :startDate"),
    @NamedQuery(name = "WorkExperience.findByEndDate", query = "SELECT w FROM WorkExperience w WHERE w.endDate = :endDate"),
    @NamedQuery(name = "WorkExperience.findByStipendAmount", query = "SELECT w FROM WorkExperience w WHERE w.stipendAmount = :stipendAmount")})
public class WorkExperience implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "company_name")
    private String companyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "role")
    private String role;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "technologies")
    private String technologies;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stipend_amount")
    private BigDecimal stipendAmount;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Students studentId;

    public WorkExperience() {
    }

    public WorkExperience(Integer id) {
        this.id = id;
    }

    public WorkExperience(Integer id, String companyName, String role, Date startDate, String technologies) {
        this.id = id;
        this.companyName = companyName;
        this.role = role;
        this.startDate = startDate;
        this.technologies = technologies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public BigDecimal getStipendAmount() {
        return stipendAmount;
    }

    public void setStipendAmount(BigDecimal stipendAmount) {
        this.stipendAmount = stipendAmount;
    }

    public Students getStudentId() {
        return studentId;
    }

    public void setStudentId(Students studentId) {
        this.studentId = studentId;
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
        if (!(object instanceof WorkExperience)) {
            return false;
        }
        WorkExperience other = (WorkExperience) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.WorkExperience[ id=" + id + " ]";
    }
    
}
