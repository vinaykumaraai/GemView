/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Audit_User_Log")
public class AuditUserLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The ID.", required = true)
    private Long id;

    @Column(name = "date_at", nullable = false)
    @ApiModelProperty(value = "The local time in which the log was performed.")
    private Timestamp dateAt;

    @Size(max = 255)
    @Column(name = "description")
    @ApiModelProperty(value = "The Description.", required = false)
    private String description;

    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "audit_user_log_type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AuditUserLogType auditUserLogType;

    @JoinColumn(name = "entity", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private com.luretechnologies.server.data.model.Entity entity;

    @JoinColumn(name = "audit_action", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AuditAction auditAction;

    @Size(max = 512)
    @Column(name = "details")
    @ApiModelProperty(value = "Details", required = false)
    private String details;

    public AuditUserLog() {
    }

    public AuditUserLog(Long id) {
        this.id = id;
    }

    public AuditUserLog(Long id, String name, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof AuditUserLog)) {
            return false;
        }
        AuditUserLog other = (AuditUserLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.AuditUserLog[ id=" + id + " ]";
    }

    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }
    /**
     * @return the datAt
     */
    public Date getDateAt() {
        return dateAt;
    }

    /**
     * @param dateAt the datAt to set
     */
    public void setDateAt(Timestamp dateAt) {
        this.dateAt = dateAt;
    }

    /**
     * @return the entity
     */
    public com.luretechnologies.server.data.model.Entity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(com.luretechnologies.server.data.model.Entity entity) {
        this.entity = entity;
    }

    /**
     * @return the auditAction
     */
    public AuditAction getAuditAction() {
        return auditAction;
    }

    /**
     * @param auditAction the auditAction to set
     */
    public void setAuditAction(AuditAction auditAction) {
        this.auditAction = auditAction;
    }

    /**
     * @return the auditUserLogType
     */
    public AuditUserLogType getAuditUserLogType() {
        return auditUserLogType;
    }

    /**
     * @param auditUserLogType the auditUserLogType to set
     */
    public void setAuditUserLogType(AuditUserLogType auditUserLogType) {
        this.auditUserLogType = auditUserLogType;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
