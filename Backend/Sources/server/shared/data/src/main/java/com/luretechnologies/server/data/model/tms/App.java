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
    @Column(name = "Name")
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    @Size(max = 20)
    @Column(name = "Version")
    @ApiModelProperty(value = "The Version.", required = true)
    private String version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "StartDate")
    @ApiModelProperty(value = "The Start Date.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EndDate")
    @ApiModelProperty(value = "The End Date.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Active")
    @ApiModelProperty(value = "The Active.", required = true)
    private short active;
    @JoinColumn(name = "Entity", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private com.luretechnologies.server.data.model.Entity entity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app")
    private Collection<AppFile> appfileCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app")
    private Collection<AppParam> appparamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app")
    private Collection<AppProfile> appprofileCollection;

    public App() {
    }

    public App(Long id) {
        this.id = id;
    }

    public App(Long id, String name, Date startDate, Date endDate, short active) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public com.luretechnologies.server.data.model.Entity getEntity() {
        return entity;
    }

    public void setEntity(com.luretechnologies.server.data.model.Entity entity) {
        this.entity = entity;
    }

    public Collection<AppFile> getAppFileCollection() {
        return appfileCollection;
    }

    public void setAppFileCollection(Collection<AppFile> appfileCollection) {
        this.appfileCollection = appfileCollection;
    }

    public Collection<AppParam> getAppParamCollection() {
        return appparamCollection;
    }

    public void setAppParamCollection(Collection<AppParam> appparamCollection) {
        this.appparamCollection = appparamCollection;
    }

    public Collection<AppProfile> getAppProfileCollection() {
        return appprofileCollection;
    }

    public void setAppProfileCollection(Collection<AppProfile> appprofileCollection) {
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
    
}
