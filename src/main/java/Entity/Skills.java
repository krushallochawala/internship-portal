/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "skills")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skills.findAll", query = "SELECT s FROM Skills s"),
    @NamedQuery(name = "Skills.findById", query = "SELECT s FROM Skills s WHERE s.id = :id"),
    @NamedQuery(name = "Skills.findBySkill", query = "SELECT s FROM Skills s WHERE s.skill = :skill")})
public class Skills implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "skill")
    private String skill;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
    private Collection<StudentSkill> studentSkillCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
    private Collection<InternshipSkills> internshipSkillsCollection;

    public Skills() {
    }

    public Skills(Integer id) {
        this.id = id;
    }

    public Skills(Integer id, String skill) {
        this.id = id;
        this.skill = skill;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @XmlTransient
    public Collection<StudentSkill> getStudentSkillCollection() {
        return studentSkillCollection;
    }

    public void setStudentSkillCollection(Collection<StudentSkill> studentSkillCollection) {
        this.studentSkillCollection = studentSkillCollection;
    }

    @XmlTransient
    public Collection<InternshipSkills> getInternshipSkillsCollection() {
        return internshipSkillsCollection;
    }

    public void setInternshipSkillsCollection(Collection<InternshipSkills> internshipSkillsCollection) {
        this.internshipSkillsCollection = internshipSkillsCollection;
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
        if (!(object instanceof Skills)) {
            return false;
        }
        Skills other = (Skills) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Skills[ id=" + id + " ]";
    }
    
}
