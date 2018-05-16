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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "App_Param_Format")
@Table(name = "App_Param_Format")
@NamedQueries({
    @NamedQuery(name = "AppParamFormat.findAll", query = "SELECT a FROM App_Param_Format a")})
public class AppParamFormat implements Serializable {

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
    @Column(name = "Value")
    @ApiModelProperty(value = "The Value.", required = true)
    private String value;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appParamFormat")
    private Collection<AppParam> appparamCollection;

    public AppParamFormat() {
    }

    public AppParamFormat(Long id) {
        this.id = id;
    }

    public AppParamFormat(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Collection<AppParam> getAppParamCollection() {
        return appparamCollection;
    }

    public void setAppParamCollection(Collection<AppParam> appparamCollection) {
        this.appparamCollection = appparamCollection;
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
        if (!(object instanceof AppParamFormat)) {
            return false;
        }
        AppParamFormat other = (AppParamFormat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.luretechnologies.server.data.model.tms.AppParamFormat[ id=" + id + " ]";
    }
    
}
