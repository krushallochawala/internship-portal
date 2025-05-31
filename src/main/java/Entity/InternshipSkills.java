/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.json.bind.annotation.JsonbTransient;
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
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "internship_skills")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InternshipSkills.findAll", query = "SELECT i FROM InternshipSkills i"),
    @NamedQuery(name = "InternshipSkills.findById", query = "SELECT i FROM InternshipSkills i WHERE i.id = :id")})
public class InternshipSkills implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "internship_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Internships internshipId;
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Skills skillId;

    public InternshipSkills() {
    }

    public InternshipSkills(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Internships getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(Internships internshipId) {
        this.internshipId = internshipId;
    }

    public Skills getSkillId() {
        return skillId;
    }

    public void setSkillId(Skills skillId) {
        this.skillId = skillId;
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
        if (!(object instanceof InternshipSkills)) {
            return false;
        }
        InternshipSkills other = (InternshipSkills) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.InternshipSkills[ id=" + id + " ]";
    }
    
}
