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
@Table(name = "education")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Education.findAll", query = "SELECT e FROM Education e"),
    @NamedQuery(name = "Education.findById", query = "SELECT e FROM Education e WHERE e.id = :id"),
    @NamedQuery(name = "Education.findByDegree", query = "SELECT e FROM Education e WHERE e.degree = :degree"),
    @NamedQuery(name = "Education.findByInstituteName", query = "SELECT e FROM Education e WHERE e.instituteName = :instituteName"),
    @NamedQuery(name = "Education.findByBoardName", query = "SELECT e FROM Education e WHERE e.boardName = :boardName"),
    @NamedQuery(name = "Education.findByStartYear", query = "SELECT e FROM Education e WHERE e.startYear = :startYear"),
    @NamedQuery(name = "Education.findByEndYear", query = "SELECT e FROM Education e WHERE e.endYear = :endYear"),
    @NamedQuery(name = "Education.findByPercentage", query = "SELECT e FROM Education e WHERE e.percentage = :percentage"),
    @NamedQuery(name = "Education.findBySpecialization", query = "SELECT e FROM Education e WHERE e.specialization = :specialization")})
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "degree")
    private String degree;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "institute_name")
    private String instituteName;
    @Size(max = 10)
    @Column(name = "board_name")
    private String boardName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_year")
//    @Temporal(TemporalType.DATE)
    private Integer startYear;
    @Column(name = "end_year")
//    @Temporal(TemporalType.DATE)
    private Integer endYear;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentage")
    private Double percentage;
    @Size(max = 30)
    @Column(name = "specialization")
    private String specialization;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Students studentId;

    public Education() {
    }

    public Education(Integer id) {
        this.id = id;
    }

    public Education(Integer id, String degree, String instituteName, Integer startYear) {
        this.id = id;
        this.degree = degree;
        this.instituteName = instituteName;
        this.startYear = startYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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
        if (!(object instanceof Education)) {
            return false;
        }
        Education other = (Education) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Education[ id=" + id + " ]";
    }
    
}
