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

@Entity(name = "App_Param")
@Table(name = "App_Param")
@NamedQueries({
    @NamedQuery(name = "AppParam.findAll", query = "SELECT a FROM App_Param a")})
public class AppParam implements Serializable {

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
    @Size(max = 512)
    @Column(name = "DefaultValue")
    @ApiModelProperty(value = "The DefaultValue.", required = true)
    private String defaultValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Modifiable")
    @ApiModelProperty(value = "The Modifiable.", required = true)
    private short modifiable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Min")
    @ApiModelProperty(value = "The Min.", required = true)
    private int min;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Max")
    @ApiModelProperty(value = "The Max.", required = true)
    private int max;
    @Size(max = 256)
    @Column(name = "`Restrict`")
    @ApiModelProperty(value = "The Restrict.", required = true)
    private String restrict;
    @Size(max = 256)
    @Column(name = "Regex")
    @ApiModelProperty(value = "The Regex.", required = true)
    private String regex;
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
    @JoinColumn(name = "App", referencedColumnName = "id")
    @ApiModelProperty(value = "The App.", required = true)
    @ManyToOne(optional = false)
    private App app;
    @JoinColumn(name = "EntityLevel", referencedColumnName = "id")
    @ApiModelProperty(value = "The EntityLevel.", required = true)
    @ManyToOne(optional = false)
    private EntityLevel entityLevel;
    @JoinColumn(name = "AppParamFormat", referencedColumnName = "id")
    @ApiModelProperty(value = "The AppParamFormat.", required = true)
    @ManyToOne(optional = false)
    private AppParamFormat appParamFormat;
    @JoinColumn(name = "Action", referencedColumnName = "id")
    @ApiModelProperty(value = "The Action.", required = true)
    @ManyToOne(optional = false)
    private Action action;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appParam")
    private Collection<AppProfileParamValue> appprofileparamvalueCollection;

    public AppParam() {
    }

    public AppParam(Long id) {
        this.id = id;
    }

    public AppParam(Long id, String name, short modifiable, int min, int max, short forceUpdate, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.modifiable = modifiable;
        this.min = min;
        this.max = max;
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getRestrict() {
        return restrict;
    }

    public void setRestrict(String restrict) {
        this.restrict = restrict;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
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

    public EntityLevel getEntityLevel() {
        return entityLevel;
    }

    public void setEntityLevel(EntityLevel entityLevel) {
        this.entityLevel = entityLevel;
    }

    public AppParamFormat getAppParamFormat() {
        return appParamFormat;
    }

    public void setAppParamFormat(AppParamFormat appParamFormat) {
        this.appParamFormat = appParamFormat;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Collection<AppProfileParamValue> getAppProfileParamValueCollection() {
        return appprofileparamvalueCollection;
    }

    public void setAppProfileParamValueCollection(Collection<AppProfileParamValue> appprofileparamvalueCollection) {
        this.appprofileparamvalueCollection = appprofileparamvalueCollection;
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
        if (!(object instanceof AppParam)) {
            return false;
        }
        AppParam other = (AppParam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.AppParam[ id=" + id + " ]";
    }

}
