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
@Table(name = "student_feedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentFeedback.findAll", query = "SELECT s FROM StudentFeedback s"),
    @NamedQuery(name = "StudentFeedback.findById", query = "SELECT s FROM StudentFeedback s WHERE s.id = :id"),
    @NamedQuery(name = "StudentFeedback.findByRating", query = "SELECT s FROM StudentFeedback s WHERE s.rating = :rating"),
    @NamedQuery(name = "StudentFeedback.findByCreatedAt", query = "SELECT s FROM StudentFeedback s WHERE s.createdAt = :createdAt")})
public class StudentFeedback implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private int rating;
    @Lob
    @Size(max = 65535)
    @Column(name = "comments")
    private String comments;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Students studentId;
    @JoinColumn(name = "internship_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Internships internshipId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public StudentFeedback() {
    }

    public StudentFeedback(Integer id) {
        this.id = id;
    }

    public StudentFeedback(Integer id, int rating, Date createdAt) {
        this.id = id;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        if (!(object instanceof StudentFeedback)) {
            return false;
        }
        StudentFeedback other = (StudentFeedback) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.StudentFeedback[ id=" + id + " ]";
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Students getStudentId() {
        return studentId;
    }

    public void setStudentId(Students studentId) {
        this.studentId = studentId;
    }

    public Internships getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Internships internshipId) {
        this.internshipId = internshipId;
    }
    
}
