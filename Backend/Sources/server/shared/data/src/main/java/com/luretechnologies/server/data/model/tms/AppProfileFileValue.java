/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model.tms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
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

@Entity(name = "App_Profile_File_Value")
@Table(name = "App_Profile_File_Value")
@NamedQueries({
    @NamedQuery(name = "AppProfileFileValue.findAll", query = "SELECT a FROM App_Profile_File_Value a")})
public class AppProfileFileValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The ID.", required = true)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "default_value")
    @ApiModelProperty(value = "The DefaultValue.", required = true)
    private String defaultValue;
    
    @Column(name = "force_update", nullable = false, length = 1)
    @ApiModelProperty(value = "The Force Update.")
    private boolean forceUpdate = false;
    
    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "app_profile")
    @ApiModelProperty(value = "The appProfile id.", required = true)
    private Long appProfileId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "app_file")
    @ApiModelProperty(value = "The app_file id.", required = true)
    private Long appFileId;

    public AppProfileFileValue() {
    }

    public AppProfileFileValue(Long id) {
        this.id = id;
    }

    public AppProfileFileValue(Long id, String defaultValue, boolean forceUpdate) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.forceUpdate = forceUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getAppProfileId() {
        return appProfileId;
    }

    public void setAppProfileId(Long appProfileId) {
        this.appProfileId = appProfileId;
    }

    public Long getAppFileId() {
        return appFileId;
    }

    public void setAppFileId(Long appFileId) {
        this.appFileId = appFileId;
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
        if (!(object instanceof AppProfileFileValue)) {
            return false;
        }
        AppProfileFileValue other = (AppProfileFileValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.App_Profile_File_Value[ id=" + id + " ]";
    }
    
}
