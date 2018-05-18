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

@Entity(name = "App_File")
@Table(name = "App_File")
@NamedQueries({
    @NamedQuery(name = "AppFile.findAll", query = "SELECT a FROM App_File a")})
public class AppFile implements Serializable {

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
    @Size(max = 128)
    @Column(name = "Description")
    @ApiModelProperty(value = "The Description.", required = true)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "DefaultValue")
    @ApiModelProperty(value = "The Default Value.", required = true)
    private String defaultValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Modifiable")
    @ApiModelProperty(value = "The Modifiable.", required = true)
    private short modifiable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ForceUpdate")
    @ApiModelProperty(value = "The Force Update.", required = true)
    private short forceUpdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UpdatedAt")
    @ApiModelProperty(value = "The UpdateAt.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @JoinColumn(name = "App", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private App app;
    @JoinColumn(name = "AppFileFormat", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AppFileFormat appFileFormat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appFile")
    private Collection<AppProfileFileValue> appprofilefilevalueCollection;

    public AppFile() {
    }

    public AppFile(Long id) {
        this.id = id;
    }

    public AppFile(Long id, String name, String defaultValue, short modifiable, short forceUpdate, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.defaultValue = defaultValue;
        this.modifiable = modifiable;
        this.forceUpdate = forceUpdate;
        this.updatedAt = updatedAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public short getModifiable() {
        return modifiable;
    }

    public void setModifiable(short modifiable) {
        this.modifiable = modifiable;
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public AppFileFormat getAppFileFormat() {
        return appFileFormat;
    }

    public void setAppFileFormat(AppFileFormat appFileFormat) {
        this.appFileFormat = appFileFormat;
    }

    public Collection<AppProfileFileValue> getAppProfileFileValueCollection() {
        return appprofilefilevalueCollection;
    }

    public void setAppProfileFileValueCollection(Collection<AppProfileFileValue> appprofilefilevalueCollection) {
        this.appprofilefilevalueCollection = appprofilefilevalueCollection;
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
        if (!(object instanceof AppFile)) {
            return false;
        }
        AppFile other = (AppFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.AppFile[ id=" + id + " ]";
    }
    
}
