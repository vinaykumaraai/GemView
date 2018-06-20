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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity
@Table(name = "App_Profile_Param_Value")
public class AppProfileParamValue implements Serializable {

    @Column(name = "id", nullable = false, length = 20)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "The database identification.")
    private Long id;
    
    @Column(name = "default_value", nullable = false, length = 512)
    @ApiModelProperty(value = "The DefaultValue.")
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
    @Column(name = "app_param")
    @ApiModelProperty(value = "The appParam id.", required = true)
    private Long appParamId;
    
    public AppProfileParamValue() {
    }

    public AppProfileParamValue(Long id) {
        this.id = id;
    }

    public AppProfileParamValue(Long id, boolean forceUpdate) {
        this.id = id;
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

    public Long getAppParamId() {
        return appParamId;
    }

    public void setAppParamId(Long appParamId) {
        this.appParamId = appParamId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }    
}
