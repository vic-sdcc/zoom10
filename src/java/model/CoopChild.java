/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vic
 */
@Entity
@Table(name = "coop_child")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoopChild.findAll", query = "SELECT c FROM CoopChild c"),
    @NamedQuery(name = "CoopChild.findByChildId", query = "SELECT c FROM CoopChild c WHERE c.childId = :childId")})
public class CoopChild implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "child_id")
    private Integer childId;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private CoopPerson personId;
    @JoinColumn(name = "family_id", referencedColumnName = "family_id")
    @ManyToOne
    private CoopFamily familyId;

    public CoopChild() {
    }

    public CoopChild(Integer childId) {
        this.childId = childId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public CoopPerson getPersonId() {
        return personId;
    }

    public void setPersonId(CoopPerson personId) {
        this.personId = personId;
    }

    public CoopFamily getFamilyId() {
        return familyId;
    }

    public void setFamilyId(CoopFamily familyId) {
        this.familyId = familyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childId != null ? childId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoopChild)) {
            return false;
        }
        CoopChild other = (CoopChild) object;
        if ((this.childId == null && other.childId != null) || (this.childId != null && !this.childId.equals(other.childId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CoopChild[ childId=" + childId + " ]";
    }
    
}
