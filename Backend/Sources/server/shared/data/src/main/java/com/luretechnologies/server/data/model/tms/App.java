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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
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

@Entity(name = "App")
@Table(name = "App")
@NamedQueries({
    @NamedQuery(name = "App.findAll", query = "SELECT a FROM App a")})
public class App implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @ApiModelProperty(value = "The id.", required = true)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    @Size(min = 1, max = 128)
    @Column(name = "description")
    @ApiModelProperty(value = "The Description.", required = true)
    private String description;
    @Size(max = 20)
    @Column(name = "version")
    @ApiModelProperty(value = "The Version.", required = true)
    private String version;
    
    @JsonIgnore
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;
    
    @JsonIgnore
    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;
    
    @Column(name = "active", nullable = false, length = 1)
    @ApiModelProperty(value = "The active.")
    private boolean active = true;
    
    @Column(name = "available", nullable = false, length = 1)
    @ApiModelProperty(value = "The available.")
    private boolean available = true;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "owner_id")
    @ApiModelProperty(value = "The entity id.", required = true)
    private Long ownerId;
        
    /*@OneToMany(mappedBy = "appId", targetEntity = AppFile.class, fetch = FetchType.LAZY)
    private Set<AppFile> appfileCollection = new HashSet<>();*/
    
    @OneToMany(mappedBy = "appId", targetEntity = AppParam.class, fetch = FetchType.LAZY)
    private Set<AppParam> appparamCollection = new HashSet<>();
    
    @OneToMany(mappedBy = "appId", targetEntity = AppProfile.class, fetch = FetchType.LAZY)
    private Set<AppProfile> appprofileCollection = new HashSet<>();

    public App() {
    }

    public App(Long id) {
        this.id = id;
    }

    public App(Long id, String name, String description, boolean available, boolean active, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.active = active;
        this.ownerId = ownerId;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /*public Set<AppFile> getAppFileCollection() {
        return appfileCollection;
    }

    public void setAppFileCollection(Set<AppFile> appfileCollection) {
        this.appfileCollection = appfileCollection;
    }*/

    public Set<AppParam> getAppParamCollection() {
        return appparamCollection;
    }

    public void setAppParamCollection(Set<AppParam> appparamCollection) {
        this.appparamCollection = appparamCollection;
    }

    public Set<AppProfile> getAppProfileCollection() {
        return appprofileCollection;
    }

    public void setAppProfileCollection(Set<AppProfile> appprofileCollection) {
        this.appprofileCollection = appprofileCollection;
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
        if (!(object instanceof App)) {
            return false;
        }
        App other = (App) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.App[ id=" + id + " ]";
    }

    /**
     * @return the ownerId
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the available
     */
    public boolean getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
}
