/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "internships")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Internships.findAll", query = "SELECT i FROM Internships i"),
    @NamedQuery(name = "Internships.findById", query = "SELECT i FROM Internships i WHERE i.id = :id"),
    @NamedQuery(name = "Internships.findByTitle", query = "SELECT i FROM Internships i WHERE i.title = :title"),
    @NamedQuery(name = "Internships.findByInternshipType", query = "SELECT i FROM Internships i WHERE i.internshipType = :internshipType"),
    @NamedQuery(name = "Internships.findByDuration", query = "SELECT i FROM Internships i WHERE i.duration = :duration"),
    @NamedQuery(name = "Internships.findByLocation", query = "SELECT i FROM Internships i WHERE i.location = :location"),
    @NamedQuery(name = "Internships.findByStipend", query = "SELECT i FROM Internships i WHERE i.stipend = :stipend"),
    @NamedQuery(name = "Internships.findByPaid", query = "SELECT i FROM Internships i WHERE i.paid = :paid"),
    @NamedQuery(name = "Internships.findByInternshipTime", query = "SELECT i FROM Internships i WHERE i.internshipTime = :internshipTime"),
    @NamedQuery(name = "Internships.findByTotalRequiredInterns", query = "SELECT i FROM Internships i WHERE i.totalRequiredInterns = :totalRequiredInterns"),
    @NamedQuery(name = "Internships.findByApplicationDeadline", query = "SELECT i FROM Internships i WHERE i.applicationDeadline = :applicationDeadline"),
    @NamedQuery(name = "Internships.findByStatus", query = "SELECT i FROM Internships i WHERE i.status = :status"),
    @NamedQuery(name = "Internships.findByCreatedAt", query = "SELECT i FROM Internships i WHERE i.createdAt = :createdAt")})
public class Internships implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "internship_type")
    private String internshipType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "duration")
    private String duration;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "paid")
    private boolean paid;
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "internship_time")
    private String internshipTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internshipId")
    @JsonbTransient
    private Collection<StudentFeedback> studentFeedbackCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stipend")
    private BigDecimal stipend;
    @Column(name = "total_required_interns")
    private Integer totalRequiredInterns;
    @Column(name = "application_deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDeadline;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internshipId")
    @JsonbTransient
    private Collection<StudentInternships> studentInternshipsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internshipId")
    @JsonbTransient
    private Collection<Payments> paymentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internshipId")
    @JsonbTransient
    private Collection<InternshipSkills> internshipSkillsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internshipId")
    @JsonbTransient
    private Collection<Bookmarks> bookmarksCollection;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categories categoryId;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Companies companyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "internshipId")
    @JsonbTransient
    private Collection<Applications> applicationsCollection;

    public Internships() {
    }

    public Internships(Integer id) {
        this.id = id;
    }

    public Internships(Integer id, String title, String internshipType, String duration, String location, boolean paid, String internshipTime, String status, Date createdAt) {
        this.id = id;
        this.title = title;
        this.internshipType = internshipType;
        this.duration = duration;
        this.location = location;
        this.paid = paid;
        this.internshipTime = internshipTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getInternshipType() {
        return internshipType;
    }

    public void setInternshipType(String internshipType) {
        this.internshipType = internshipType;
    }


    public BigDecimal getStipend() {
        return stipend;
    }

    public void setStipend(BigDecimal stipend) {
        this.stipend = stipend;
    }


    public String getInternshipTime() {
        return internshipTime;
    }

    public void setInternshipTime(String internshipTime) {
        this.internshipTime = internshipTime;
    }

    public Integer getTotalRequiredInterns() {
        return totalRequiredInterns;
    }

    public void setTotalRequiredInterns(Integer totalRequiredInterns) {
        this.totalRequiredInterns = totalRequiredInterns;
    }

    public Date getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(Date applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @XmlTransient
    public Collection<StudentInternships> getStudentInternshipsCollection() {
        return studentInternshipsCollection;
    }

    public void setStudentInternshipsCollection(Collection<StudentInternships> studentInternshipsCollection) {
        this.studentInternshipsCollection = studentInternshipsCollection;
    }

    @XmlTransient
    public Collection<Payments> getPaymentsCollection() {
        return paymentsCollection;
    }

    public void setPaymentsCollection(Collection<Payments> paymentsCollection) {
        this.paymentsCollection = paymentsCollection;
    }

    @XmlTransient
    public Collection<InternshipSkills> getInternshipSkillsCollection() {
        return internshipSkillsCollection;
    }

    public void setInternshipSkillsCollection(Collection<InternshipSkills> internshipSkillsCollection) {
        this.internshipSkillsCollection = internshipSkillsCollection;
    }

    @XmlTransient
    public Collection<Bookmarks> getBookmarksCollection() {
        return bookmarksCollection;
    }

    public void setBookmarksCollection(Collection<Bookmarks> bookmarksCollection) {
        this.bookmarksCollection = bookmarksCollection;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }

    public Companies getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Companies companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public Collection<Applications> getApplicationsCollection() {
        return applicationsCollection;
    }

    public void setApplicationsCollection(Collection<Applications> applicationsCollection) {
        this.applicationsCollection = applicationsCollection;
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
        if (!(object instanceof Internships)) {
            return false;
        }
        Internships other = (Internships) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Internships[ id=" + id + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<StudentFeedback> getStudentFeedbackCollection() {
        return studentFeedbackCollection;
    }

    public void setStudentFeedbackCollection(Collection<StudentFeedback> studentFeedbackCollection) {
        this.studentFeedbackCollection = studentFeedbackCollection;
    }
    
}
