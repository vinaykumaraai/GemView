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
    @Column(name = "name")
    @ApiModelProperty(value = "The Name.", required = true)
    private String name;
    @Size(max = 128)
    @Column(name = "description")
    @ApiModelProperty(value = "The Description.", required = true)
    private String description;
    @Size(max = 512)
    @Column(name = "default_value")
    @ApiModelProperty(value = "The DefaultValue.", required = true)
    private String defaultValue;
    
    @Column(name = "modifiable", nullable = false, length = 1)
    @ApiModelProperty(value = "The modifiable.")
    private boolean modifiable = true;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "`min`")
    @ApiModelProperty(value = "The Min.", required = true)
    private int min;
    @Basic(optional = false)
    @NotNull
    @Column(name = "`max`")
    @ApiModelProperty(value = "The Max.", required = true)
    private int max;
    @Size(max = 256)
    @Column(name = "`restrict`")
    @ApiModelProperty(value = "The Restrict.", required = true)
    private String restrict;
    @Size(max = 256)
    @Column(name = "regex")
    @ApiModelProperty(value = "The Regex.", required = true)
    private String regex;
    
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
    
    @JoinColumn(name = "Entity_Level", referencedColumnName = "id")
    @ApiModelProperty(value = "The EntityLevel.", required = true)
    @ManyToOne(optional = false)
    private EntityLevel entityLevel;
    
    @JoinColumn(name = "App_Param_Format", referencedColumnName = "id")
    @ApiModelProperty(value = "The AppParamFormat.", required = true)
    @ManyToOne(optional = false)
    private AppParamFormat appParamFormat;
    
    @JoinColumn(name = "Action", referencedColumnName = "id")
    @ApiModelProperty(value = "The Action.", required = true)
    @ManyToOne(optional = false)
    private Action action;
    
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "appParam", fetch = FetchType.LAZY)
    private Collection<AppProfileParamValue> appprofileparamvalueCollection;*/

    public AppParam() {
    }

    public AppParam(Long id) {
        this.id = id;
    }

    public AppParam(Long id, String name, boolean modifiable, int min, int max, boolean forceUpdate) {
        this.id = id;
        this.name = name;
        this.modifiable = modifiable;
        this.min = min;
        this.max = max;
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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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

    /*public Collection<AppProfileParamValue> getAppProfileParamValueCollection() {
        return appprofileparamvalueCollection;
    }

    public void setAppProfileParamValueCollection(Collection<AppProfileParamValue> appprofileparamvalueCollection) {
        this.appprofileparamvalueCollection = appprofileparamvalueCollection;
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
