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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vic
 */
@Entity
@Table(name = "coop_member_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoopMemberStatus.findAll", query = "SELECT c FROM CoopMemberStatus c"),
    @NamedQuery(name = "CoopMemberStatus.findByStatusId", query = "SELECT c FROM CoopMemberStatus c WHERE c.statusId = :statusId"),
    @NamedQuery(name = "CoopMemberStatus.findByStatusName", query = "SELECT c FROM CoopMemberStatus c WHERE c.statusName = :statusName"),
    @NamedQuery(name = "CoopMemberStatus.findByMemStatus", query = "SELECT c FROM CoopMemberStatus c WHERE c.memStatus = :memStatus")})
public class CoopMemberStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "status_id")
    private Integer statusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "status_name")
    private String statusName;
    @Size(max = 1)
    @Column(name = "mem_status")
    private String memStatus;

    public CoopMemberStatus() {
    }

    public CoopMemberStatus(Integer statusId) {
        this.statusId = statusId;
    }

    public CoopMemberStatus(Integer statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getMemStatus() {
        return memStatus;
    }

    public void setMemStatus(String memStatus) {
        this.memStatus = memStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoopMemberStatus)) {
            return false;
        }
        CoopMemberStatus other = (CoopMemberStatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CoopMemberStatus[ statusId=" + statusId + " ]";
    }
    
}
