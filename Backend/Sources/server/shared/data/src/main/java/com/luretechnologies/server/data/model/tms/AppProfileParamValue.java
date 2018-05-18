/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model.tms;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "App_Profile_Param_Value")
@Table(name = "App_Profile_Param_Value")
@NamedQueries({
    @NamedQuery(name = "AppProfileParamValue.findAll", query = "SELECT a FROM App_Profile_Param_Value a")})
public class AppProfileParamValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The ID.", required = true)
    private Long id;
    @Size(max = 512)
    @Column(name = "DefaultValue")
    @ApiModelProperty(value = "The DefaultValue.", required = true)
    private String defaultValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ForceUpdate")
    @ApiModelProperty(value = "The ForceUpdate.", required = true)
    private short forceUpdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UpdatedAt")
    @ApiModelProperty(value = "The UpdateAt.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appProfileParamValue")
    private Collection<ApplianceAppParamValue> applianceappparamvalueCollection;
    @JoinColumn(name = "AppProfile", referencedColumnName = "id")
    @ApiModelProperty(value = "The AppProfile.", required = true)
    @ManyToOne(optional = false)
    private AppProfile appProfile;
    @JoinColumn(name = "AppParam", referencedColumnName = "id")
    @ApiModelProperty(value = "The AppParam.", required = true)
    @ManyToOne(optional = false)
    private AppParam appParam;

    public AppProfileParamValue() {
    }

    public AppProfileParamValue(Long id) {
        this.id = id;
    }

    public AppProfileParamValue(Long id, short forceUpdate, Date updatedAt) {
        this.id = id;
        this.forceUpdate = forceUpdate;
        this.updatedAt = updatedAt;
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

    public short getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(short forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<ApplianceAppParamValue> getApplianceAppParamValueCollection() {
        return applianceappparamvalueCollection;
    }

    public void setApplianceAppParamValueCollection(Collection<ApplianceAppParamValue> applianceappparamvalueCollection) {
        this.applianceappparamvalueCollection = applianceappparamvalueCollection;
    }

    public AppProfile getAppProfile() {
        return appProfile;
    }

    public void setAppProfile(AppProfile appProfile) {
        this.appProfile = appProfile;
    }

    public AppParam getAppParam() {
        return appParam;
    }

    public void setAppParam(AppParam appParam) {
        this.appParam = appParam;
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
        if (!(object instanceof AppProfileParamValue)) {
            return false;
        }
        AppProfileParamValue other = (AppProfileParamValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.App_Profile_Param_Value[ id=" + id + " ]";
    }
    
}
