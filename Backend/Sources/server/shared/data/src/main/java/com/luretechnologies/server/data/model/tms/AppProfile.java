/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luretechnologies.server.data.model.tms;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Collection;
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
    @Column(name = "Name")
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Active")
    @ApiModelProperty(value = "The Active.", required = true)
    private short active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appProfile")
    private Collection<AppProfileParamValue> appprofileparamvalueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appProfile")
    private Collection<ApplianceApp> applianceappCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appProfile")
    private Collection<AppProfileFileValue> appprofilefilevalueCollection;
    @JoinColumn(name = "App", referencedColumnName = "id")
    @ApiModelProperty(value = "The App.", required = true)
    @ManyToOne(optional = false)
    private App app;

    public AppProfile() {
    }

    public AppProfile(Long id) {
        this.id = id;
    }

    public AppProfile(Long id, String name, short active) {
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

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public Collection<AppProfileParamValue> getAppProfileParamValueCollection() {
        return appprofileparamvalueCollection;
    }

    public void setAppProfileParamValueCollection(Collection<AppProfileParamValue> appprofileparamvalueCollection) {
        this.appprofileparamvalueCollection = appprofileparamvalueCollection;
    }

    public Collection<ApplianceApp> getApplianceAppCollection() {
        return applianceappCollection;
    }

    public void setApplianceAppCollection(Collection<ApplianceApp> applianceappCollection) {
        this.applianceappCollection = applianceappCollection;
    }

    public Collection<AppProfileFileValue> getAppProfileFileValueCollection() {
        return appprofilefilevalueCollection;
    }

    public void setAppProfileFileValueCollection(Collection<AppProfileFileValue> appprofilefilevalueCollection) {
        this.appprofilefilevalueCollection = appprofilefilevalueCollection;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
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
