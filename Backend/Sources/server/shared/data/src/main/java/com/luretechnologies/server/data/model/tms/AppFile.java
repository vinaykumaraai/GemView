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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @Column(name = "name")
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    @Size(max = 128)
    @Column(name = "description")
    @ApiModelProperty(value = "The Description.", required = true)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "default_value")
    @ApiModelProperty(value = "The Default Value.", required = true)
    private String defaultValue;
    
    @Column(name = "modifiable", nullable = false, length = 1)
    @ApiModelProperty(value = "The modifiable.")
    private boolean modifiable = true;
    @Column(name = "force_update", nullable = false, length = 1)
    @ApiModelProperty(value = "The Force Update.")
    private boolean forceUpdate = false;
    
    @JsonIgnore
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
        
    @Basic(optional = false)
    @NotNull
    @Column(name = "app")
    @ApiModelProperty(value = "The app id.", required = true)
    private Long appId;
    
    
    @JoinColumn(name = "App_File_Format", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @ApiModelProperty(value = "The AppFileFormat.", required = true)
    private AppFileFormat appFileFormat;
    
    /*@OneToMany(mappedBy = "appFileId", targetEntity = AppProfileFileValue.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AppProfileFileValue> appprofilefilevalueCollection = new HashSet<>();*/
    
    public AppFile() {
    }

    public AppFile(Long id) {
        this.id = id;
    }

    public AppFile(Long id, String name, String defaultValue, boolean modifiable, boolean forceUpdate) {
        this.id = id;
        this.name = name;
        this.defaultValue = defaultValue;
        this.modifiable = modifiable;
        this.forceUpdate = forceUpdate;
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

    public boolean getModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
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

    public void setUpdatedAt(Timestamp value) {
        this.updatedAt = value;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public AppFileFormat getAppFileFormat() {
        return appFileFormat;
    }

    public void setAppFileFormat(AppFileFormat appFileFormat) {
        this.appFileFormat = appFileFormat;
    }

    /*public Set<AppProfileFileValue> getAppProfileFileValueCollection() {
        return appprofilefilevalueCollection;
    }

    public void setAppProfileFileValueCollection(Set<AppProfileFileValue> appprofilefilevalueCollection) {
        this.appprofilefilevalueCollection = appprofilefilevalueCollection;
    }*/

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
