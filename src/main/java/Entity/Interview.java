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
import java.util.Date;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "interview")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Interview.findAll", query = "SELECT i FROM Interview i"),
    @NamedQuery(name = "Interview.findById", query = "SELECT i FROM Interview i WHERE i.id = :id"),
    @NamedQuery(name = "Interview.findByInterviewDate", query = "SELECT i FROM Interview i WHERE i.interviewDate = :interviewDate"),
    @NamedQuery(name = "Interview.findByInterviewSchedule", query = "SELECT i FROM Interview i WHERE i.interviewSchedule = :interviewSchedule"),
    @NamedQuery(name = "Interview.findByInterviewStatus", query = "SELECT i FROM Interview i WHERE i.interviewStatus = :interviewStatus")})
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "interview_date")
    @Temporal(TemporalType.DATE)
    private Date interviewDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "interview_schedule")
    @Temporal(TemporalType.TIME)
    private Date interviewSchedule;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "interview_status")
    private String interviewStatus;
    @Lob
    @Size(max = 65535)
    @Column(name = "feedback")
    private String feedback;
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Applications applicationId;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Companies companyId;

    public Interview() {
    }

    public Interview(Integer id) {
        this.id = id;
    }

    public Interview(Integer id, Date interviewDate, Date interviewSchedule, String interviewStatus) {
        this.id = id;
        this.interviewDate = interviewDate;
        this.interviewSchedule = interviewSchedule;
        this.interviewStatus = interviewStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Date getInterviewSchedule() {
        return interviewSchedule;
    }

    public void setInterviewSchedule(Date interviewSchedule) {
        this.interviewSchedule = interviewSchedule;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Applications getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Applications applicationId) {
        this.applicationId = applicationId;
    }

    public Companies getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Companies companyId) {
        this.companyId = companyId;
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
        if (!(object instanceof Interview)) {
            return false;
        }
        Interview other = (Interview) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Interview[ id=" + id + " ]";
    }
    
}
