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
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "companies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Companies.findAll", query = "SELECT c FROM Companies c"),
    @NamedQuery(name = "Companies.findById", query = "SELECT c FROM Companies c WHERE c.id = :id"),
    @NamedQuery(name = "Companies.findByName", query = "SELECT c FROM Companies c WHERE c.name = :name"),
    @NamedQuery(name = "Companies.findByLogo", query = "SELECT c FROM Companies c WHERE c.logo = :logo"),
    @NamedQuery(name = "Companies.findByWebsite", query = "SELECT c FROM Companies c WHERE c.website = :website"),
    @NamedQuery(name = "Companies.findByContactPerson", query = "SELECT c FROM Companies c WHERE c.contactPerson = :contactPerson"),
    @NamedQuery(name = "Companies.findByEmail", query = "SELECT c FROM Companies c WHERE c.email = :email"),
    @NamedQuery(name = "Companies.findByPassword", query = "SELECT c FROM Companies c WHERE c.password = :password"),
    @NamedQuery(name = "Companies.findByContact", query = "SELECT c FROM Companies c WHERE c.contact = :contact"),
    @NamedQuery(name = "Companies.findByCreatedAt", query = "SELECT c FROM Companies c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "Companies.findByUpdatedAt", query = "SELECT c FROM Companies c WHERE c.updatedAt = :updatedAt")})
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "logo")
    private String logo;
    @Size(max = 100)
    @Column(name = "website")
    private String website;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "contact_person")
    private String contactPerson;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Internships> internshipsCollection;
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Roles roleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId")
    private Collection<Interview> interviewCollection;

    public Companies() {
    }

    public Companies(Integer id) {
        this.id = id;
    }

    public Companies(Integer id, String name, String contactPerson, String email, String password, String contact, String address, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.contactPerson = contactPerson;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @XmlTransient
    public Collection<Internships> getInternshipsCollection() {
        return internshipsCollection;
    }

    public void setInternshipsCollection(Collection<Internships> internshipsCollection) {
        this.internshipsCollection = internshipsCollection;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    @XmlTransient
    public Collection<Interview> getInterviewCollection() {
        return interviewCollection;
    }

    public void setInterviewCollection(Collection<Interview> interviewCollection) {
        this.interviewCollection = interviewCollection;
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
        if (!(object instanceof Companies)) {
            return false;
        }
        Companies other = (Companies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Companies[ id=" + id + " ]";
    }
    
}
