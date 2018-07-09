/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model.tms;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "App_Profile")
@Table(name = "App_Profile")
@NamedQueries({
    @NamedQuery(name = "AppProfile.findAll", query = "SELECT a FROM App_Profile a")})
public class AppProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The ID.", required = true)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    
    @Column(name = "active", nullable = false, length = 1)
    @ApiModelProperty(value = "The active.")
    private boolean active = true;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appProfileId", targetEntity = AppProfileParamValue.class, fetch = FetchType.LAZY)
    private Set<AppProfileParamValue> appprofileparamvalueCollection = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appProfileId", targetEntity = EntityAppProfile.class, fetch = FetchType.LAZY)
    private Set<EntityAppProfile> entityappprofileCollection = new HashSet<>();
    
    /*@OneToMany(mappedBy = "appProfileId", targetEntity = AppProfileFileValue.class, fetch = FetchType.LAZY)
    private Set<AppProfileFileValue> appprofilefilevalueCollection = new HashSet<>();*/
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "app")
    @ApiModelProperty(value = "The app id.", required = true)
    private Long appId;

    public AppProfile() {
    }

    public AppProfile(Long id) {
        this.id = id;
    }

    public AppProfile(Long id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<AppProfileParamValue> getAppProfileParamValueCollection() {
        return appprofileparamvalueCollection;
    }

    public void setAppProfileParamValueCollection(Set<AppProfileParamValue> appprofileparamvalueCollection) {
        this.appprofileparamvalueCollection = appprofileparamvalueCollection;
    }
    
    /*public Set<AppProfileFileValue> getAppProfileFileValueCollection() {
        return appprofilefilevalueCollection;
    }

    public void setAppProfileFileValueCollection(Set<AppProfileFileValue> appprofilefilevalueCollection) {
        this.appprofilefilevalueCollection = appprofilefilevalueCollection;
    }*/
    
    /**
     * @return the entityappprofileCollection
     */
    public Set<EntityAppProfile> getEntityAppProfileCollection() {
        return entityappprofileCollection;
    }

    /**
     * @param entityappprofileCollection the entityappprofileCollection to set
     */
    public void setEntityAppProfileCollection(Set<EntityAppProfile> entityappprofileCollection) {
        this.entityappprofileCollection = entityappprofileCollection;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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
        if (!(object instanceof AppProfile)) {
            return false;
        }
        AppProfile other = (AppProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.App_Profile[ id=" + id + " ]";
    }  
}
