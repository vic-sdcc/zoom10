/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vic
 */
@Entity
@Table(name = "coop_family")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoopFamily.findAll", query = "SELECT c FROM CoopFamily c"),
    @NamedQuery(name = "CoopFamily.findByFamilyId", query = "SELECT c FROM CoopFamily c WHERE c.familyId = :familyId")})
public class CoopFamily implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "family_id")
    private Integer familyId;
    @JoinColumn(name = "wife", referencedColumnName = "person_id")
    @ManyToOne
    private CoopPerson wife;
    @JoinColumn(name = "husband", referencedColumnName = "person_id")
    @ManyToOne
    private CoopPerson husband;
    @OneToMany(mappedBy = "familyId")
    private Collection<CoopChild> coopChildCollection;

    public CoopFamily() {
    }

    public CoopFamily(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public CoopPerson getWife() {
        return wife;
    }

    public void setWife(CoopPerson wife) {
        this.wife = wife;
    }

    public CoopPerson getHusband() {
        return husband;
    }

    public void setHusband(CoopPerson husband) {
        this.husband = husband;
    }

    @XmlTransient
    public Collection<CoopChild> getCoopChildCollection() {
        return coopChildCollection;
    }

    public void setCoopChildCollection(Collection<CoopChild> coopChildCollection) {
        this.coopChildCollection = coopChildCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (familyId != null ? familyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoopFamily)) {
            return false;
        }
        CoopFamily other = (CoopFamily) object;
        if ((this.familyId == null && other.familyId != null) || (this.familyId != null && !this.familyId.equals(other.familyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CoopFamily[ familyId=" + familyId + " ]";
    }
    
}
