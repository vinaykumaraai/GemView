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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appProfileFileValue")
    private Collection<ApplianceAppFileValue> applianceappfilevalueCollection;
    @JoinColumn(name = "AppProfile", referencedColumnName = "id")
    @ApiModelProperty(value = "The AppProfile.", required = true)
    @ManyToOne(optional = false)
    private AppProfile appProfile;
    @JoinColumn(name = "AppFile", referencedColumnName = "id")
    @ApiModelProperty(value = "The AppFile.", required = true)
    @ManyToOne(optional = false)
    private AppFile appFile;

    public AppProfileFileValue() {
    }

    public AppProfileFileValue(Long id) {
        this.id = id;
    }

    public AppProfileFileValue(Long id, String defaultValue, short forceUpdate, Date updatedAt) {
        this.id = id;
        this.defaultValue = defaultValue;
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

    public Collection<ApplianceAppFileValue> getApplianceAppFileValueCollection() {
        return applianceappfilevalueCollection;
    }

    public void setApplianceAppFileValueCollection(Collection<ApplianceAppFileValue> applianceappfilevalueCollection) {
        this.applianceappfilevalueCollection = applianceappfilevalueCollection;
    }

    public AppProfile getAppProfile() {
        return appProfile;
    }

    public void setAppProfile(AppProfile appProfile) {
        this.appProfile = appProfile;
    }

    public AppFile getAppFile() {
        return appFile;
    }

    public void setAppFile(AppFile appFile) {
        this.appFile = appFile;
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
