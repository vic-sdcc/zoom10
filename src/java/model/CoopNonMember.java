/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vic
 */
@Entity
@Table(name = "coop_non_member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoopNonMember.findAll", query = "SELECT c FROM CoopNonMember c"),
    @NamedQuery(name = "CoopNonMember.findByNonMemberId", query = "SELECT c FROM CoopNonMember c WHERE c.nonMemberId = :nonMemberId"),
    @NamedQuery(name = "CoopNonMember.findByLastName", query = "SELECT c FROM CoopNonMember c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "CoopNonMember.findByFirstName", query = "SELECT c FROM CoopNonMember c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "CoopNonMember.findByMiddleName", query = "SELECT c FROM CoopNonMember c WHERE c.middleName = :middleName"),
    @NamedQuery(name = "CoopNonMember.findByBirthdate", query = "SELECT c FROM CoopNonMember c WHERE c.birthdate = :birthdate"),
    @NamedQuery(name = "CoopNonMember.findByGender", query = "SELECT c FROM CoopNonMember c WHERE c.gender = :gender")})
public class CoopNonMember implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "non_member_id")
    private String nonMemberId;
    @Size(max = 20)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 20)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 20)
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "gender")
    private Character gender;

    public CoopNonMember() {
    }

    public CoopNonMember(String nonMemberId) {
        this.nonMemberId = nonMemberId;
    }

    public String getNonMemberId() {
        return nonMemberId;
    }

    public void setNonMemberId(String nonMemberId) {
        this.nonMemberId = nonMemberId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nonMemberId != null ? nonMemberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoopNonMember)) {
            return false;
        }
        CoopNonMember other = (CoopNonMember) object;
        if ((this.nonMemberId == null && other.nonMemberId != null) || (this.nonMemberId != null && !this.nonMemberId.equals(other.nonMemberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CoopNonMember[ nonMemberId=" + nonMemberId + " ]";
    }
    
}
