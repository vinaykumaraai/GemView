/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.client.restlib.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luretechnologies.client.restlib.common.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;

public class AuditUserLog {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Timestamp dateAt;
    private String description;
    private User user;
    private AuditUserLogType auditUserLogType;
    private Entity entity;
    private AuditAction auditAction;
    private String details;

    public AuditUserLog() {

    }

    public AuditUserLog(Long id, String name, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     *
     * @return
     */
    @ApiModelProperty(value = "Id")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(value = "Description")
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the datAt
     */
    @ApiModelProperty(value = "Date")
    @JsonProperty("dateAt")
    public Timestamp getDateAt() {
        return dateAt;
    }

    /**
     * @param dateAt the datAt to set
     */
    public void setDateAt(Timestamp dateAt) {
        this.dateAt = dateAt;
    }

    /**
     * @return the user
     */
    @ApiModelProperty(value = "User")
    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the auditUserLogType
     */
    @ApiModelProperty(value = "Audit User Log Type")
    @JsonProperty("auditUserLogType")
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
     * @return the entity
     */
    @ApiModelProperty(value = "Entity")
    @JsonProperty("entity")
    public Entity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * @return the auditAction
     */
    @ApiModelProperty(value = "Audit Action")
    @JsonProperty("auditAction")
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
     * @return the details
     */
    @ApiModelProperty(value = "Details")
    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuditUserLog {\n");
        sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
        sb.append("    datAt: ").append(StringUtil.toIndentedString(dateAt)).append("\n");
        sb.append("    description: ").append(StringUtil.toIndentedString(description)).append("\n");
        sb.append("    user: ").append(StringUtil.toIndentedString(user)).append("\n");
        sb.append("    auditUserLogType: ").append(StringUtil.toIndentedString(auditUserLogType)).append("\n");
        sb.append("    entity: ").append(StringUtil.toIndentedString(entity)).append("\n");
        sb.append("    auditAction: ").append(StringUtil.toIndentedString(auditAction)).append("\n");
        sb.append("    details: ").append(StringUtil.toIndentedString(details)).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
